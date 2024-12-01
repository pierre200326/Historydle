package com.historydle.demo.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.historydle.demo.Entity.Partie;
import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.PersonnageRepository;
import com.historydle.demo.Repository.PartieRepository;
import com.historydle.demo.Repository.UtilisateurRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfilUtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PartieRepository partieRepository;

    @Autowired
    private PersonnageRepository personnageRepository;

@GetMapping("/profilUser")
public String afficherProfilUser(HttpSession session, Model model) {
    String pseudoUtilisateurConnecte = (String) session.getAttribute("username");

    if (pseudoUtilisateurConnecte == null) {
        return "redirect:/login";
    }

    // Lire l'historique et filtrer les données pour l'utilisateur
    List<String[]> historiqueUtilisateur = new ArrayList<>();
    try (BufferedReader csvReader = new BufferedReader(new FileReader("historique.csv"))) {
        String ligne;
        while ((ligne = csvReader.readLine()) != null) {
            String[] data = ligne.split(",");
            if (data.length >= 4 && data[0].trim().equalsIgnoreCase(pseudoUtilisateurConnecte.trim())) {
                historiqueUtilisateur.add(data);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    Utilisateur utilisateur = utilisateurRepository.findByPseudo(pseudoUtilisateurConnecte)
                                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    List<Partie> parties = partieRepository.findByUtilisateur(utilisateur);

    // Ajouter l'historique et les autres attributs au modèle
    model.addAttribute("utilisateur", pseudoUtilisateurConnecte);
    model.addAttribute("historique", historiqueUtilisateur);
    model.addAttribute("parties", parties);
    model.addAttribute("personnagesLikes", utilisateur.getPersonnagesLikes());
    return "profilUser";
}

@PostMapping("/dislike/{id}")
public String dislikePersonnage(@PathVariable Long id, HttpSession session) {
    String username = (String) session.getAttribute("username");
    if (username != null) {
        Utilisateur utilisateur = utilisateurRepository.findByPseudo(username) .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));;
        Personnage personnage = personnageRepository.findById(id).orElseThrow(() -> new RuntimeException("Personnage non trouvé"));

        if (utilisateur != null && personnage != null) {
            utilisateur.getPersonnagesLikes().remove(personnage);
            utilisateurRepository.save(utilisateur);
        }
    }
    return "redirect:/profilUser"; // Redirige vers la page du profil après le dislike
}

}