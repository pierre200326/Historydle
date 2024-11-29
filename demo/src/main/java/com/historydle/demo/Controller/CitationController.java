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

import jakarta.servlet.http.HttpSession;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CitationController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponseCitationController reponseCitationController;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PartieRepository partieRepository;

    private final List<Map<String, Object>> resultats = new ArrayList<>();
    private int tourDeJeu = 0; // Initialiser le compteur de tours


    @GetMapping("/citation")
    public String citation(Model model,HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        }else{
            System.out.println("Utilisateur connecté : " + username);
        }

        model.addAttribute("resultats", resultats);
        // Obtenir la citation du personnage à deviner
        Personnage reponseDuJour = reponseCitationController.getReponseDuJour();
        model.addAttribute("tourDeJeu", tourDeJeu);
        model.addAttribute("ageDisponibleDans", Math.max(0, 3 - tourDeJeu)); // Limiter à 0 minimum
        model.addAttribute("titreDisponibleDans", Math.max(0, 6 - tourDeJeu)); // Limiter à 0 minimum
        model.addAttribute("hasCorrectName", resultats.stream().anyMatch(resultat -> Boolean.TRUE.equals(resultat.get("nomCorrect"))));
        if (reponseDuJour != null) {
            model.addAttribute("citation", reponseDuJour.getCitation());
        } else {
            model.addAttribute("citation", "Pas de citation disponible pour aujourd'hui.");
        }
        return "citation";
    }

    @GetMapping("/autocompleteCitation")
    @ResponseBody
    public List<String> autocompleteCitation(@RequestParam("query") String query) {
        List<Personnage> personnages = personnageRepository.findByNomContainingIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());
        }
        return suggestions;
    }


    @PostMapping("/verifierReponseCitation")
    public String verifierReponseCitation(@RequestParam("reponse") String reponseUtilisateur, Model model, HttpSession session) {
        String pseudoUtilisateurConnecte = (String) session.getAttribute("username");
        Utilisateur utilisateur = null;
        if (pseudoUtilisateurConnecte != null) {
            utilisateur = utilisateurRepository.findByPseudo(pseudoUtilisateurConnecte)
                            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        }

        Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);

        // Récupérer la réponse du jour
        Personnage reponseDuJour = reponseCitationController.getReponseDuJour();
        tourDeJeu++;

        // Créer une carte pour stocker les résultats
        Map<String, Object> resultat = new HashMap<>();
        boolean nomCorrect = false;
        List<Indice> indices = reponseDuJour.getIndices(); // Récupérer les indices du personnage


        if (personnageUtilisateur != null) {
            // Récupérer les attributs du personnage de l'utilisateur
            String nomUtilisateur = personnageUtilisateur.getNom();

            // Comparer avec la réponse du jour
            nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);
            String indiceAge=indices.get(0).getIndice();
            String indiceTitre=indices.get(1).getIndice();
    
            resultat.put("nom", nomUtilisateur);
            resultat.put("nomCorrect", nomCorrect);
            resultat.put("indiceAge", indiceAge);
            resultat.put("indiceTitre", indiceTitre);
        } else {
            // Si le personnage n'est pas trouvé, gérer ce cas
            resultat.put("nom", reponseUtilisateur);
            resultat.put("nomCorrect", false);  // par défaut faux si pas trouvé
        }

        if (nomCorrect && utilisateur != null) {
        // Si le joueur est connecté et a deviné correctement, enregistrer la partie
        Partie nouvellePartie = new Partie("Citation", personnageUtilisateur.getNom(), utilisateur);
        partieRepository.save(nouvellePartie);

        // Enregistrer dans un fichier CSV
        try (FileWriter csvWriter = new FileWriter("historique.csv", true)) {
            csvWriter.append(utilisateur.getPseudo());
            csvWriter.append(",");
            csvWriter.append(personnageUtilisateur.getNom());
            csvWriter.append(",");
            csvWriter.append("Citation");
            csvWriter.append(",");
            csvWriter.append(java.time.LocalDate.now().toString()); // Date de l'enregistrement
            csvWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        // Ajouter le résultat à la liste
        resultats.add(0, resultat); 
        model.addAttribute("resultats", resultats);
        model.addAttribute("hasCorrectName", nomCorrect);

        return "redirect:/citation";
    }

}