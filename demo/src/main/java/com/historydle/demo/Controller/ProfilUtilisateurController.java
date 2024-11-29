package com.historydle.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.historydle.demo.Entity.Partie;
import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.PartieRepository;
import com.historydle.demo.Repository.UtilisateurRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfilUtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PartieRepository partieRepository;



    @GetMapping("/profilUser")
    public String afficherProfilUser(HttpSession session, Model model) {
    String pseudoUtilisateurConnecte = (String) session.getAttribute("username");

    if (pseudoUtilisateurConnecte == null) {
        return "redirect:/login";
    }

    Utilisateur utilisateur = utilisateurRepository.findByPseudo(pseudoUtilisateurConnecte)
                                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    List<Partie> parties = partieRepository.findByUtilisateur(utilisateur);

    model.addAttribute("utilisateur", pseudoUtilisateurConnecte);
    model.addAttribute("parties", parties);

    return "profilUser";
}

}