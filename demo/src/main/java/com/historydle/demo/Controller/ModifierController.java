package com.historydle.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.UtilisateurService;

@Controller
public class ModifierController {
    private final UtilisateurService utilisateurService;

    @Autowired
    public ModifierController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/modifier")
    public String accueil(Model model) {
        // Récupère tous les utilisateurs
        List<Utilisateur> utilisateurs = utilisateurService.findAllUtilisateurs();
        // Passe les utilisateurs au modèle
        model.addAttribute("utilisateurs", utilisateurs);
        return "modifier";
    }

    @PostMapping("/modifier-utilisateur")
    public String modifierUtilisateur(@RequestParam("id") Long id,
                                       @RequestParam("pseudo") String pseudo,
                                       @RequestParam("mdp") String mdp) {
        // Appeler le service pour modifier l'utilisateur
        utilisateurService.modifierUtilisateur(id, pseudo, mdp);
        utilisateurService.ecrireUtilisateursCsv();
        return "redirect:/modifier";
    }
}
