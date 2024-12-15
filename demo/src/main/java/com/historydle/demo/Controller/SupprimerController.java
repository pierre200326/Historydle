package com.historydle.demo.Controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.UtilisateurService;

@Controller
public class SupprimerController {

    private final UtilisateurService utilisateurService;
    // Initialisation des fonctions de Utilisateurs
    public SupprimerController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Affiche la page admin supprimer tout en envoyant la liste des utilisateurs
    @GetMapping("/supprimer")
    public String accueil(Model model) {
        // Récupère tous les utilisateurs
        List<Utilisateur> utilisateurs = utilisateurService.findAllUtilisateurs();
        // Passe les utilisateurs au modèle
        model.addAttribute("utilisateurs", utilisateurs);
        return "supprimer";
    }
    // Supprime l'utilisateur qui a le pseudo correspondant
    @PostMapping("/supprimer-utilisateur")
    public String supprimerUtilisateur(@RequestParam("pseudo") String pseudo) {
        boolean success = utilisateurService.supprimerUtilisateur(pseudo);

        if (success) {
            System.out.println("Utilisateur supprimé : " + pseudo);
        } else {
            System.err.println("Échec de la suppression. Utilisateur introuvable : " + pseudo);
        }

        supprimerLigneCsv("./data/utilisateurs.csv", pseudo);

        return "redirect:/supprimer"; // Redirection vers une page d'administration
    }
    //Supprime la ligne du pseudo correspondant dans le csv
    public static void supprimerLigneCsv(String cheminFichier, String pseudo) {
        File fichierCsv = new File(cheminFichier);
        File fichierTemp = new File(fichierCsv.getParent(), "temp.csv");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(fichierCsv));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichierTemp))
        ) {
            String ligne;
            boolean ligneSupprimee = false;

            // Lire chaque ligne du fichier
            while ((ligne = reader.readLine()) != null) {
                String[] colonnes = ligne.split(",");
                // Vérifier si le pseudo correspond
                if (colonnes[0].equals(pseudo)) {
                    ligneSupprimee = true; // Marquer la ligne comme supprimée
                } else {
                    // Réécrire la ligne dans le fichier temporaire
                    writer.write(ligne);
                    writer.newLine();
                }
            }

            if (!ligneSupprimee) {
                System.out.println("Pseudo introuvable : " + pseudo);
            } else {
                System.out.println("Utilisateur supprimé : " + pseudo);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }

        // Remplacer le fichier CSV d'origine par le fichier temporaire
        if (!fichierTemp.renameTo(fichierCsv)) {
            System.err.println("Erreur lors du remplacement du fichier CSV.");
        }
    }

}
