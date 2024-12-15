package com.historydle.demo.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import jakarta.servlet.http.HttpSession;

// Controller pour gérer le jeu "Citation"
@Controller
public class CitationController {

    @Autowired
    private PersonnageRepository personnageRepository;  // Permet l'accès aux données des personnages

    @Autowired
    private ReponseCitationController reponseCitationController;  // Permet de récupérer la réponse du jour pour le jeu

    @Autowired
    private UtilisateurRepository utilisateurRepository;  // Permet de récupérer les données des utilisateurs

    @Autowired
    private PartieRepository partieRepository;  // Permet de gérer les données des parties jouées

    private final List<Map<String, Object>> resultats = new ArrayList<>();  // Liste pour stocker les résultats des réponses des joueurs
    private int tourDeJeu = 0;  // Compteur pour suivre le nombre de tours joués dans le jeu

    // Méthode pour afficher la page de jeu
    @GetMapping("/citation")
    public String citation(Model model, HttpSession session) {

        // Récupère l'utilisateur connecté à partir de la session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");  // Si aucun utilisateur n'est connecté, affiche ce message
        } else {
            System.out.println("Utilisateur connecté : " + username);  // Affiche le nom de l'utilisateur connecté
        }

        // Ajoute les attributs nécessaires au modèle pour afficher les résultats
        model.addAttribute("resultats", resultats);
        // Récupère la citation du personnage à deviner du jour via le contrôleur ReponseCitationController
        Personnage reponseDuJour = reponseCitationController.getReponseDuJour();
        model.addAttribute("tourDeJeu", tourDeJeu);  // Ajoute le nombre de tours au modèle
        model.addAttribute("ageDisponibleDans", Math.max(0, 3 - tourDeJeu));  // Limite l'affichage des indices en fonction des tours
        model.addAttribute("titreDisponibleDans", Math.max(0, 6 - tourDeJeu));  // Limite l'affichage des indices en fonction des tours
        model.addAttribute("hasCorrectName", resultats.stream().anyMatch(resultat -> Boolean.TRUE.equals(resultat.get("nomCorrect"))));  // Vérifie si un nom correct a été trouvé
        if (reponseDuJour != null) {
            model.addAttribute("citation", reponseDuJour.getCitation());  // Affiche la citation du personnage à deviner
        } else {
            model.addAttribute("citation", "Pas de citation disponible pour aujourd'hui.");  // Si aucune citation n'est disponible, affiche ce message
        }
        return "citation";  // Retourne la vue "citation" qui contient le jeu
    }

    // Méthode pour gérer l'autocomplétion des noms de personnages
    @GetMapping("/autocompleteCitation")
    @ResponseBody
    public List<String> autocompleteCitation(@RequestParam("query") String query) {
        // Recherche des personnages dont le nom contient la chaîne "query" (insensible à la casse)
        List<Personnage> personnages = personnageRepository.findByNomContainingIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());  // Ajoute les noms correspondants à la liste des suggestions
        }
        return suggestions;  // Retourne les suggestions en réponse
    }

    // Méthode pour vérifier si la réponse de l'utilisateur est correcte
    @PostMapping("/verifierReponseCitation")
    public String verifierReponseCitation(@RequestParam("reponse") String reponseUtilisateur, Model model, HttpSession session) {
        // Récupère l'utilisateur connecté à partir de la session
        String pseudoUtilisateurConnecte = (String) session.getAttribute("username");
        Utilisateur utilisateur = null;
        if (pseudoUtilisateurConnecte != null) {
            utilisateur = utilisateurRepository.findByPseudo(pseudoUtilisateurConnecte)
                            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));  // Trouve l'utilisateur en fonction de son pseudo
        }

        // Récupère le personnage correspondant au nom saisi par l'utilisateur
        Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);

        // Récupère la réponse du jour
        Personnage reponseDuJour = reponseCitationController.getReponseDuJour();
        tourDeJeu++;  // Incrémente le compteur de tours
        // Crée une carte pour stocker les résultats de la réponse de l'utilisateur
        Map<String, Object> resultat = new HashMap<>();
        boolean nomCorrect = false;  // Indique si le nom de l'utilisateur est correct
        List<Indice> indices = reponseDuJour.getIndices();  // Récupère les indices du personnage du jour

        if (personnageUtilisateur != null) {
            // Récupère les attributs du personnage de l'utilisateur
            String nomUtilisateur = personnageUtilisateur.getNom();

            // Compare le nom de l'utilisateur avec le nom du personnage du jour
            nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);
            String indiceAge = indices.get(0).getIndice();  // Récupère le premier indice (l'âge)
            String indiceTitre = indices.get(1).getIndice();  // Récupère le deuxième indice (le titre)

            // Ajoute les résultats à la carte
            resultat.put("nom", nomUtilisateur);
            resultat.put("nomCorrect", nomCorrect);
            resultat.put("indiceAge", indiceAge);
            resultat.put("indiceTitre", indiceTitre);
        } else {
            // Si le personnage n'est pas trouvé, gère ce cas
            resultat.put("nom", reponseUtilisateur);
            resultat.put("nomCorrect", false);  // Par défaut, le nom est incorrect si le personnage n'est pas trouvé
        }

        // Si l'utilisateur a deviné correctement et est connecté, enregistre la partie
        if (nomCorrect && utilisateur != null) {
            Partie nouvellePartie = new Partie("Citation", personnageUtilisateur.getNom(), utilisateur);
            partieRepository.save(nouvellePartie);  // Sauvegarde la partie dans la base de données

            // Enregistre l'historique dans un fichier CSV
            try (FileWriter csvWriter = new FileWriter("historique.csv", true)) {
                csvWriter.append(utilisateur.getPseudo());
                csvWriter.append(",");
                csvWriter.append(personnageUtilisateur.getNom());
                csvWriter.append(",");
                csvWriter.append("Citation");
                csvWriter.append(",");
                csvWriter.append(java.time.LocalDate.now().toString());  // Ajoute la date actuelle
                csvWriter.append("\n");
            } catch (IOException e) {
                e.printStackTrace();  // Si une erreur se produit lors de l'écriture dans le fichier, affiche l'erreur
            }
        }

        // Ajoute le résultat à la liste des résultats
        resultats.add(0, resultat);
        model.addAttribute("resultats", resultats);  // Met à jour le modèle avec les résultats
        model.addAttribute("hasCorrectName", nomCorrect);  // Indique si le nom était correct
        return "redirect:/citation";  
    }
}
