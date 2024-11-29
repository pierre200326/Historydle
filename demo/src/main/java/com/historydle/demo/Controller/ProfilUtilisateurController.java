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

    return "profilUser";
}


}