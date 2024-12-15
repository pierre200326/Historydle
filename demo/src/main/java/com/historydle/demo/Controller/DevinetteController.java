package com.historydle.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.historydle.demo.Entity.Indice;
import com.historydle.demo.Entity.Partie;
import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.PartieRepository;
import com.historydle.demo.Repository.PersonnageRepository;
import com.historydle.demo.Repository.UtilisateurRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

// Controller pour gérer le jeu "Devinette"
@Controller
public class DevinetteController {

    @Autowired
    private PersonnageRepository personnageRepository; // Accès aux données des personnages

    @Autowired
    private ReponseDevinetteController reponseDevinetteController; // Contrôleur pour récupérer la réponse du jour

    @Autowired
    private UtilisateurRepository utilisateurRepository; // Accès aux données des utilisateurs

    @Autowired
    private PartieRepository partieRepository; // Accès aux parties jouées

    private final List<Map<String, Object>> resultats = new ArrayList<>(); // Liste des résultats des réponses
    private int tourDeJeu = 0; // Compteur pour suivre le nombre de tours joués

    // Méthode pour afficher la page de jeu
    @GetMapping("/jouer")
    public String jouer(Model model, HttpSession session) {
        // Vérifie si un utilisateur est connecté via la session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        } else {
            System.out.println("Utilisateur connecté : " + username);
        }

        // Si un nouvel utilisateur se connecte, réinitialiser les données du jeu
        if (username != null && session.getAttribute("newUser2") == null) {
        // Initialiser ou réinitialiser les données de jeu
        resultats.clear();
        tourDeJeu = 0;
        session.setAttribute("newUser2", "true");
        }

        // Ajoute les attributs nécessaires au modèle pour afficher les résultats
        model.addAttribute("resultats", resultats);
        model.addAttribute("tourDeJeu", tourDeJeu);
        model.addAttribute("ageDisponibleDans", Math.max(0, 3 - tourDeJeu)); // Affiche un indice après 3 tours
        model.addAttribute("titreDisponibleDans", Math.max(0, 6 - tourDeJeu)); // Affiche un autre indice après 6 tours
        model.addAttribute("hasCorrectName", resultats.stream().anyMatch(resultat -> Boolean.TRUE.equals(resultat.get("nomCorrect"))));
        return "jouer"; // Retourne la vue "jouer"
    }

    // Méthode pour gérer l'autocomplétion des noms de personnages
    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam("query") String query) {
        // Recherche des personnages contenant le texte saisi
        List<Personnage> personnages = personnageRepository.findByNomContainingIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom()); // Ajoute les noms correspondants à la liste des suggestions
        }
        return suggestions; // Retourne les suggestions en réponse
    }

    // Méthode pour vérifier si la réponse de l'utilisateur est correcte
    @PostMapping("/verifierReponse")
    public String verifierReponse(@RequestParam("reponse") String reponseUtilisateur, Model model, HttpSession session) {
        // Récupère l'utilisateur connecté à partir de la session
        String pseudoUtilisateurConnecte = (String) session.getAttribute("username");
        Utilisateur utilisateur = null;
        if (pseudoUtilisateurConnecte != null) {
            utilisateur = utilisateurRepository.findByPseudo(pseudoUtilisateurConnecte)
                            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        }

        // Récupère le personnage correspondant au nom saisi
        Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);
        tourDeJeu++; // Incrémente le compteur de tours
        // Récupère la réponse du jour
        Personnage reponseDuJour = reponseDevinetteController.getReponseDuJour();
        boolean nomCorrect = false; // Indique si le nom est correct
        List<Indice> indices = reponseDuJour.getIndices(); // Liste des indices du personnage du jour

        // Initialisation d'une carte pour stocker les résultats de la réponse
        Map<String, Object> resultat = new HashMap<>();

        if (personnageUtilisateur != null) {
            // Récupère les attributs du personnage saisi par l'utilisateur
            String nomUtilisateur = personnageUtilisateur.getNom();
            String domaineUtilisateur = personnageUtilisateur.getDomaine();
            String genreUtilisateur = personnageUtilisateur.getGenre();
            String paysUtilisateur = personnageUtilisateur.getPays();
            String continentUtilisateur = personnageUtilisateur.getContinent();
            int periodeUtilisateur = personnageUtilisateur.getPeriode();

            // Compare les attributs avec ceux du personnage du jour
            nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);
            boolean domaineCorrect = reponseDuJour.getDomaine().equalsIgnoreCase(domaineUtilisateur);
            boolean domainePartiellementVrai = reponseDuJour.getDomaine().contains(domaineUtilisateur) || domaineUtilisateur.contains(reponseDuJour.getDomaine());
            boolean genreCorrect = reponseDuJour.getGenre().equalsIgnoreCase(genreUtilisateur);
            boolean paysCorrect = reponseDuJour.getPays().equalsIgnoreCase(paysUtilisateur);
            boolean paysPartiellementVrai = reponseDuJour.getPays().contains(paysUtilisateur) || paysUtilisateur.contains(reponseDuJour.getPays());
            boolean continentCorrect = reponseDuJour.getContinent().equalsIgnoreCase(continentUtilisateur);
            boolean continentPartiellementVrai = reponseDuJour.getContinent().contains(continentUtilisateur) || continentUtilisateur.contains(reponseDuJour.getContinent());
            boolean periodeCorrect = reponseDuJour.getPeriode() == periodeUtilisateur;
            boolean periodePlusVieux = periodeUtilisateur < reponseDuJour.getPeriode();
            boolean periodePlusJeune = periodeUtilisateur > reponseDuJour.getPeriode();
            String indiceAge = indices.get(0).getIndice(); // Premier indice (âge)
            String indiceTitre = indices.get(1).getIndice(); // Deuxième indice (titre)

            // Ajoute les résultats des comparaisons à la carte
            resultat.put("nom", nomUtilisateur);
            resultat.put("domaine", domaineUtilisateur);
            resultat.put("pays", paysUtilisateur);
            resultat.put("continent", continentUtilisateur);
            resultat.put("genre", genreUtilisateur);
            resultat.put("periode", periodeUtilisateur);
            resultat.put("nomCorrect", nomCorrect);
            resultat.put("domaineCorrect", domaineCorrect);
            resultat.put("domainePartiellementVrai", domainePartiellementVrai);
            resultat.put("paysCorrect", paysCorrect);
            resultat.put("paysPartiellementVrai", paysPartiellementVrai);
            resultat.put("continentCorrect", continentCorrect);
            resultat.put("continentPartiellementVrai", continentPartiellementVrai);
            resultat.put("genreCorrect", genreCorrect);
            resultat.put("periodeCorrect", periodeCorrect);
            resultat.put("periodePlusVieux", periodePlusVieux);
            resultat.put("periodePlusJeune", periodePlusJeune);
            resultat.put("indiceAge", indiceAge);
            resultat.put("indiceTitre", indiceTitre);
        } else {
            // Si le personnage n'est pas trouvé
            resultat.put("nom", reponseUtilisateur);
            resultat.put("domaine", "Personnage non trouvé");
            resultat.put("nomCorrect", false);
        }

        if (nomCorrect && utilisateur != null) {
            // Si la réponse est correcte et l'utilisateur est connecté, enregistre la partie
            Partie nouvellePartie = new Partie("Historydle", personnageUtilisateur.getNom(), utilisateur);
            partieRepository.save(nouvellePartie);

            // Écrit les informations dans un fichier CSV
            try (FileWriter csvWriter = new FileWriter("historique.csv", true)) {
                csvWriter.append(utilisateur.getPseudo());
                csvWriter.append(",");
                csvWriter.append(personnageUtilisateur.getNom());
                csvWriter.append(",");
                csvWriter.append("Historydle");
                csvWriter.append(",");
                csvWriter.append(java.time.LocalDate.now().toString());
                csvWriter.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Ajoute le résultat en tête de la liste des résultats
        resultats.add(0, resultat);

        // Ajoute les résultats au modèle
        model.addAttribute("resultats", resultats);
        model.addAttribute("hasCorrectName", nomCorrect);

        return "redirect:/jouer"; // Redirige vers la page de jeu
    }
}
