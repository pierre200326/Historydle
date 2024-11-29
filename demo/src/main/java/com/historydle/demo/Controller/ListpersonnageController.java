package com.historydle.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Repository.PersonnageRepository;

import java.util.List;

@Controller
public class ListpersonnageController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @GetMapping("/personnages")
    public String afficherPersonnages(Model model) {
        List<Personnage> personnages = personnageRepository.findAll(); // Récupère tous les personnages
        model.addAttribute("personnages", personnages); // Ajoute à la vue
        return "personnages";
    }
}
