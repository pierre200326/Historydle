package com.historydle.demo.Controller;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.UtilisateurService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ConnexionController {

    @GetMapping("/connexion")
    public String accueil(HttpSession session) {
        session.invalidate();
        return "connexion";
    }


    private final UtilisateurService utilisateurService;

    public ConnexionController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    // Méthode pour traiter la soumission du formulaire de connexion
    @PostMapping("/login")
    public String login(@RequestParam("pseudo") String username, 
                        @RequestParam("password") String password, 
                        Model model,
                        HttpSession session) {
        // Vérifie si l'utilisateur peut être authentifié
        boolean isAuthenticated = utilisateurService.authenticate(username, password);
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByPseudo(username);
        
        if (isAuthenticated) {
            // Si authentifié, redirige vers la page d'accueil ou tableau de bord
            System.out.println("connexion");
            Utilisateur utilisateur = optionalUtilisateur.get();
            String statut = utilisateur.getStatut();
            if(statut.equals("admin")){
                session.setAttribute("admin", "admin");
                return "redirect:/admin";
            }else{
                session.setAttribute("username", username);
                return "redirect:/";
            }
        } else {
            // Si l'authentification échoue, afficher un message d'erreur
            model.addAttribute("error", "Identifiants incorrects");
            System.out.println("pas connexion");
            return "connexion";  // Renvoie à la page de login avec un message d'erreur
        }
    }

}
