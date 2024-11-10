package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@Controller
public class CitationController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponseCitationController reponseCitationController;

    private final List<Map<String, Object>> resultats = new ArrayList<>();

    @GetMapping("/citation")
    public String citation(Model model) {
        model.addAttribute("resultats", resultats);
        // Obtenir la citation du personnage à deviner
        Personnage reponseDuJour = reponseCitationController.getReponseDuJour();
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
        List<Personnage> personnages = personnageRepository.findByNomStartingWithIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());
        }
        return suggestions;
    }


    @PostMapping("/verifierReponseCitation")
    public String verifierReponseCitation(@RequestParam("reponse") String reponseUtilisateur, Model model) {
        Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);

        // Récupérer la réponse du jour
        Personnage reponseDuJour = reponseCitationController.getReponseDuJour();

        // Créer une carte pour stocker les résultats
        Map<String, Object> resultat = new HashMap<>();
        boolean nomCorrect = false;

        if (personnageUtilisateur != null) {
            // Récupérer les attributs du personnage de l'utilisateur
            String nomUtilisateur = personnageUtilisateur.getNom();

            // Comparer avec la réponse du jour
            nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);

            resultat.put("nom", nomUtilisateur);
            resultat.put("nomCorrect", nomCorrect);
        } else {
            // Si le personnage n'est pas trouvé, gérer ce cas
            resultat.put("nom", reponseUtilisateur);
            resultat.put("nomCorrect", false);  // par défaut faux si pas trouvé
        }

        // Ajouter le résultat à la liste
        resultats.add(0, resultat); 
        model.addAttribute("resultats", resultats);

        return "redirect:/citation?correct=" + nomCorrect;
    }

}