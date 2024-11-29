package com.historydle.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.historydle.demo.Identity.Indice;
import com.historydle.demo.Identity.Personnage;
import com.historydle.demo.Repository.PersonnageRepository;

import jakarta.servlet.http.HttpSession;

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
    public String verifierReponseCitation(@RequestParam("reponse") String reponseUtilisateur, Model model) {
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

        // Ajouter le résultat à la liste
        resultats.add(0, resultat); 
        model.addAttribute("resultats", resultats);
        model.addAttribute("hasCorrectName", nomCorrect);

        return "redirect:/citation";
    }

}