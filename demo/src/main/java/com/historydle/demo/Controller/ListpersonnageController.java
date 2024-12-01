package com.historydle.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.PersonnageRepository;
import com.historydle.demo.Repository.UtilisateurRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class ListpersonnageController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PersonnageRepository personnageRepository;

    @GetMapping("/personnages")
    public String personnages(Model model, HttpSession session) {
        // Récupère l'utilisateur connecté à partir de la session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        } else {
            System.out.println("Utilisateur connecté : " + username);
        }

        List<Personnage> personnages = personnageRepository.findAll(); // Récupère tous les personnages
        model.addAttribute("personnages", personnages); // Ajoute à la vue
        return "personnages";
    }

    @PostMapping("/like/{personnageId}")
    public String likePersonnage(@PathVariable Long personnageId, HttpSession session) {
        // Récupère l'utilisateur connecté
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
            return "redirect:/personnages"; // Redirige si l'utilisateur n'est pas connecté
        }

        // Récupère l'utilisateur par son pseudo (ou autre critère selon ton implémentation)
        Utilisateur utilisateur = utilisateurRepository.findByPseudo(username).orElse(null);
        if (utilisateur == null) {
            return "redirect:/personnages"; // Redirige si l'utilisateur n'existe pas
        }

        // Récupère le personnage à liker
        Personnage personnage = personnageRepository.findById(personnageId).orElse(null);
        if (personnage == null) {
            return "redirect:/personnages"; // Redirige si le personnage n'existe pas
        }

        // Ajouter le personnage à la liste des personnages likés de l'utilisateur
        if (utilisateur.getPersonnagesLikes().size() < 3) {
            utilisateur.getPersonnagesLikes().add(personnage);
            utilisateurRepository.save(utilisateur); // Sauvegarde l'utilisateur avec le personnage liké
        }

        return "redirect:/personnages"; // Redirige vers la page du jeu
    }
    
}


