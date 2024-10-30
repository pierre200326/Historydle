package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
public class ReponseController {

    @Autowired
    private PersonnageRepository personnageRepository;

    private Personnage reponseDuJour;
    private LocalDate dateDuJour = null;

    private void genererReponseDuJour() {
        if (reponseDuJour == null || !LocalDate.now().equals(dateDuJour)) {
            List<Personnage> personnages = personnageRepository.findAll();
            if (!personnages.isEmpty()) {
                reponseDuJour = personnages.get(new Random().nextInt(personnages.size()));
                dateDuJour = LocalDate.now(); // Met à jour la date du jour
            }
        }
    }

    @GetMapping("/jouer/reponse") // Route différente pour éviter le conflit
    public String afficherJouer(Model model) {
        genererReponseDuJour();
        model.addAttribute("reponseDuJour", reponseDuJour.getNom());
        return "jouer"; // Assurez-vous que cela renvoie vers le bon template
    }

    @PostMapping("/verifierReponse")
    public String verifierReponse(@RequestParam("reponse") String reponseUtilisateur, Model model) {
        genererReponseDuJour();

        boolean estCorrect = reponseDuJour.getNom().equalsIgnoreCase(reponseUtilisateur);
        model.addAttribute("estCorrect", estCorrect);

        return "resultat"; // page resultat.html pour afficher si la réponse est correcte ou non
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam("query") String query) {
        // Chercher les noms de personnages qui commencent par les lettres entrées
        return personnageRepository.findByNomStartingWithIgnoreCase(query)
                                   .stream()
                                   .map(Personnage::getNom)
                                   .collect(Collectors.toList());
    }
}
