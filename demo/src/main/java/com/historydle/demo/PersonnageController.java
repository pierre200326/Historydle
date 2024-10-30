package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
}
