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

import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.PersonnageRepository;
import com.historydle.demo.Repository.UtilisateurRepository;

import jakarta.servlet.http.HttpSession;

// Controller pour gérer la page de la liste des personnages
@Controller
public class ListpersonnageController {

    @Autowired
    private UtilisateurRepository utilisateurRepository; //Accés aux données des utilisateurs

    @Autowired
    private PersonnageRepository personnageRepository; // Accès aux données des personnages

    // Méthode pour afficher la page liste des personnages
    @GetMapping("/personnages")
    public String personnages(Model model, HttpSession session) {
        // Récupère l'utilisateur connecté à partir de la session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        } else {
            System.out.println("Utilisateur connecté : " + username);
        }

        // Récupère la liste des personnages depuis le repository
        List<Personnage> personnages = personnageRepository.findOnlyPersonnage();
        model.addAttribute("personnages", personnages); // Ajoute la liste au modèle pour l'afficher dans la vue
        return "personnages";
    }

    // Méthode pour "liker" un personnage
    @PostMapping("/like/{personnageId}")
    public String likePersonnage(@PathVariable Long personnageId, HttpSession session) {
        // Récupère l'utilisateur connecté à partir de la session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
            return "redirect:/personnages"; // Redirige vers la page des personnages si l'utilisateur n'est pas connecté
        }

        // Récupère l'utilisateur à partir de son pseudo
        Utilisateur utilisateur = utilisateurRepository.findByPseudo(username).orElse(null);
        if (utilisateur == null) {
            return "redirect:/personnages"; // Redirige si l'utilisateur n'est pas trouvé
        }

        // Récupère le personnage à liker à partir de l'ID
        Personnage personnage = personnageRepository.findById(personnageId).orElse(null);
        if (personnage == null) {
            return "redirect:/personnages"; // Redirige si le personnage n'est pas trouvé
        }

        // Vérifie si l'utilisateur a déjà liké moins de 3 personnages
        if (utilisateur.getPersonnagesLikes().size() < 3) {
            utilisateur.getPersonnagesLikes().add(personnage); // Ajoute le personnage à la liste des personnages likés
            utilisateurRepository.save(utilisateur); // Sauvegarde l'utilisateur avec les modifications
            mettreAJourLigneCsv("./data/likes.csv", username, personnage.getNom()); // Met à jour le fichier CSV des likes
        }

        return "redirect:/personnages"; // Redirige vers la page des personnages après avoir liké
    }

    // Méthode pour mettre à jour le fichier CSV des likes
    public static void mettreAJourLigneCsv(String cheminFichier, String pseudo, String personnageNom) {
        List<String> lignes = new ArrayList<>();
        boolean utilisateurTrouve = false;

        // Lecture du fichier CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            // Lecture ligne par ligne du fichier CSV
            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(","); // Sépare chaque ligne par des virgules

                // Si l'utilisateur est trouvé, on ajoute le personnage à la ligne existante
                if (data[0].equalsIgnoreCase(pseudo)) {
                    utilisateurTrouve = true;
                    String nouvelleLigne = ligne + "," + personnageNom; // Ajoute le personnage à la ligne
                    lignes.add(nouvelleLigne);
                } else {
                    lignes.add(ligne); // Ajoute la ligne telle quelle si l'utilisateur n'est pas trouvé
                }
            }

            // Si l'utilisateur n'est pas trouvé, ajoute une nouvelle ligne
            if (!utilisateurTrouve) {
                lignes.add(pseudo + "," + personnageNom); // Crée une nouvelle ligne pour l'utilisateur
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gère les exceptions lors de la lecture du fichier
        }

        // Réécriture du fichier CSV avec les nouvelles données
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (String l : lignes) {
                writer.write(l); // Écrit chaque ligne dans le fichier
                writer.newLine(); // Ajoute une nouvelle ligne après chaque écriture
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gère les exceptions lors de la réécriture du fichier
        }
    }
}
