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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

        List<Personnage> personnages = personnageRepository.findOnlyPersonnage();
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
            mettreAJourLigneCsv("./data/likes.csv", username, personnage.getNom());
        }

        return "redirect:/personnages"; // Redirige vers la page du jeu
    }
    
    public static void mettreAJourLigneCsv(String cheminFichier, String pseudo, String personnageNom) {
        List<String> lignes = new ArrayList<>();
        boolean utilisateurTrouve = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                // Séparer la ligne en fonction des virgules
                String[] data = ligne.split(",");

                // Vérifier si la ligne correspond à l'utilisateur
                if (data[0].equalsIgnoreCase(pseudo)) {
                    utilisateurTrouve = true;

                    // Ajouter le personnage à la liste s'il n'est pas déjà présent
                    String nouvelleLigne = ligne + "," + personnageNom;
                    lignes.add(nouvelleLigne);
                } else {
                    lignes.add(ligne);
                }
            }

            // Si aucune ligne n'a été trouvée pour l'utilisateur, ajouter une nouvelle ligne
            if (!utilisateurTrouve) {
                lignes.add(pseudo + "," + personnageNom);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Réécriture du fichier avec les nouvelles données
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (String l : lignes) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}


