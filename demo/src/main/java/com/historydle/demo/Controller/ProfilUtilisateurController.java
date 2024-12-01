package com.historydle.demo.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        Utilisateur utilisateur = utilisateurRepository.findByPseudo(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Personnage personnage = personnageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personnage non trouvé"));

        if (utilisateur.getPersonnagesLikes().remove(personnage)) {
            utilisateurRepository.save(utilisateur);
            supprimerPersonnageCsv("data/likes.csv", username, personnage.getNom());
        }
    }
    return "redirect:/profilUser";
}

public void supprimerPersonnageCsv(String cheminFichier, String pseudo, String personnageNom) {
    List<String> lignesModifiees = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] data = ligne.split(",");

            // Si la ligne correspond à l'utilisateur, on filtre les personnages
            if (data[0].equalsIgnoreCase(pseudo)) {
                StringBuilder nouvelleLigne = new StringBuilder(data[0]);
                for (int i = 1; i < data.length; i++) {
                    if (!data[i].trim().equalsIgnoreCase(personnageNom.trim())) {
                        nouvelleLigne.append(",").append(data[i]);
                    }
                }

                // Ajouter la ligne mise à jour si elle contient encore des personnages
                if (nouvelleLigne.toString().contains(",")) {
                    lignesModifiees.add(nouvelleLigne.toString());
                }
            } else {
                lignesModifiees.add(ligne); // Conserver les lignes des autres utilisateurs
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Réécrire le fichier avec les lignes modifiées
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
        for (String ligne : lignesModifiees) {
            writer.write(ligne);
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}