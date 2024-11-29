package com.historydle.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfilUtilisateurController {

    @GetMapping("/profilUser")
    public String afficherProfilUser(HttpSession session, Model model) {
    String pseudoUtilisateurConnecte = (String) session.getAttribute("username");

    if (pseudoUtilisateurConnecte == null) {
        return "redirect:/login";
    }

    model.addAttribute("utilisateur", pseudoUtilisateurConnecte);
    return "profilUser";
}
}
