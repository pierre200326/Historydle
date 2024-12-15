package com.historydle.demo.Controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.historydle.demo.Entity.PersonnageSuggestion;
import com.historydle.demo.SuggestionService;

@Controller
public class VoirSuggestionController {
    private final SuggestionService suggestionService;

    public VoirSuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/voirSuggestion")
    public String accueil(Model model) {
        // Récupère tous les utilisateurs
        List<PersonnageSuggestion> suggestion = suggestionService.findAllUtilisateurs();
        // Passe les utilisateurs au modèle
        model.addAttribute("suggestion", suggestion);
        return "voirSuggestion";
    }
}
