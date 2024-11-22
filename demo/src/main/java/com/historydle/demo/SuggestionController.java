package com.historydle.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SuggestionController {

    @GetMapping("/suggestion")
    public String showSuggestionForm(Model model) {
        model.addAttribute("suggestion", new SuggestionDto());
        return "suggestion";
    }

    @PostMapping("/suggestion")
    public String submitSuggestion(@ModelAttribute("suggestion") SuggestionDto suggestion) {
        // Logique pour envoyer la suggestion à l'admin (par exemple via un email ou enregistrement en base)
        System.out.println("Suggestion reçue : " + suggestion);
        // Ajout d'une confirmation visuelle pour l'utilisateur
        return "redirect:/suggestion?success=true";
    }
}