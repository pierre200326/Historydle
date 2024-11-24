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
import jakarta.servlet.http.HttpSession;

@Controller
public class PortraitController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponsePortraitController reponsePortraitController;

    private final List<Map<String, Object>> resultats = new ArrayList<>();

     @GetMapping("/portrait")
    public String portrait(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        }else{
            System.out.println("Utilisateur connecté : " + username);
        }

        model.addAttribute("resultats",resultats);
        Personnage reponseDuJour = reponsePortraitController.getReponseDuJour();
        if (reponseDuJour!= null){
            model.addAttribute("portrait", reponseDuJour.getNom());
        }
        else{
            model.addAttribute("portrait", "Pas de portrait disponible pour aujourd'hui");
        }

        return "portrait";
    }


    @GetMapping("/autocompletePortrait")
    @ResponseBody
    public List<String> autocompletePortrait(@RequestParam("query") String query) {
        List<Personnage> personnages = personnageRepository.findByNomContainingIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());
        }
        return suggestions;
    }

    @PostMapping("/verifierReponsePortrait")
    public String verifierReponsePortrait(@RequestParam("reponse") String reponseUtilisateur, Model model) {
    // Récupérer le personnage correspondant au nom saisi par l'utilisateur
    Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);

    // Récupérer la réponse du jour
    Personnage reponseDuJour = reponsePortraitController.getReponseDuJour();

    // Créer une carte pour stocker les résultats
    Map<String, Object> resultat = new HashMap<>();
    boolean nomCorrect = false;

    if (personnageUtilisateur != null) {
        // Récupérer les attributs du personnage guess
        String nomUtilisateur = personnageUtilisateur.getNom();


        // Comparer avec la réponse du jour
        nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);

        // Ajouter les résultats à la carte
        resultat.put("nom", nomUtilisateur);

        resultat.put("nomCorrect", nomCorrect);

    } else {
        // Si le personnage n'est pas trouvé, gérer ce cas
        resultat.put("nom", reponseUtilisateur);
        resultat.put("nomCorrect", false);
    }

    // Ajouter le résultat à la liste
    resultats.add(0, resultat); 
    model.addAttribute("resultats", resultats);

    return "redirect:/portrait?correct=" + nomCorrect;
}

}

