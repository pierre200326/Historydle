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

@Controller
public class PersonnageController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponseController reponseController;

    private final List<Map<String, Object>> resultats = new ArrayList<>();

    @GetMapping("/jouer")
    public String jouer(Model model) {
        model.addAttribute("resultats", resultats);
        return "jouer";
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam("query") String query) {
        List<Personnage> personnages = personnageRepository.findByNomStartingWithIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());
        }
        return suggestions;
    }

    @PostMapping("/verifierReponse")
    public String verifierReponse(@RequestParam("reponse") String reponseUtilisateur, Model model) {
    // Récupérer le personnage correspondant au nom saisi par l'utilisateur
    Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);

    // Récupérer la réponse du jour
    Personnage reponseDuJour = reponseController.getReponseDuJour();

    // Créer une carte pour stocker les résultats
    Map<String, Object> resultat = new HashMap<>();

    if (personnageUtilisateur != null) {
        // Récupérer les attributs du personnage de l'utilisateur
        String nomUtilisateur = personnageUtilisateur.getNom();
        String domaineUtilisateur = personnageUtilisateur.getDomaine();

        // Comparer avec la réponse du jour
        boolean nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);
        boolean domaineCorrect = reponseDuJour.getDomaine().equalsIgnoreCase(domaineUtilisateur);

        // Ajouter les résultats à la carte
        resultat.put("nom", nomUtilisateur);
        resultat.put("domaine", domaineUtilisateur);
        resultat.put("nomCorrect", nomCorrect);
        resultat.put("domaineCorrect", domaineCorrect);
    } else {
        // Si le personnage n'est pas trouvé, gérer ce cas
        resultat.put("nom", reponseUtilisateur);
        resultat.put("domaine", "Personnage non trouvé");
        resultat.put("nomCorrect", false);
        resultat.put("domaineCorrect", false);
    }

    // Ajouter le résultat à la liste
    resultats.add(resultat);
    model.addAttribute("resultats", resultats);

    return "redirect:/jouer";
}

}
