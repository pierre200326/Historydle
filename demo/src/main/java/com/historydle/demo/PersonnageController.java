package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PersonnageController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @GetMapping("/")
    public String accueil() {
        return "index";
    }

    @GetMapping("/personnages")
    public String personnages(Model model) {
        List<Personnage> personnages = personnageRepository.findAll();
        model.addAttribute("personnages", personnages);
        return "personnages";
    }

    @GetMapping("/jouer")
    public String jouer() {
        return "jouer"; // indique à Thymeleaf d'aller chercher jouer.html dans le dossier templates
    }
    
    // Méthode pour l'endpoint d'autocomplétion
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