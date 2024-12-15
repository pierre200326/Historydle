package com.historydle.demo.Controller;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.historydle.demo.UtilisateurService;

@Controller
public class InscriptionController {

    @GetMapping("/inscription")
    public String accueil() {
        return "inscription";
    }

    private final UtilisateurService utilisateurService;
    //Initialisation des fonctions de Utilisateurs
    public InscriptionController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    // Méthode pour traiter la soumission du formulaire de connexion
    @PostMapping("/register")
    public String register(@RequestParam("pseudo") String username, 
                        @RequestParam("password") String password, 
                        @RequestParam("confirm-password") String confirm_password,
                        Model model) {
        // On regarde si le mot de passe et le même que le mot de passe confirmé
        if(password.equals(confirm_password)){
            boolean pseudoIndisponible = utilisateurService.existeParPseudo(username);
            if(pseudoIndisponible){
                System.err.println("Pseudo Indisponible : " + username);
                model.addAttribute("error", "Pseudo Indisponible");
                return "redirect:/inscription";
            }else{
                String[] nouvelleLigne = {username, password};
                ajouterLigneCsv("./data/utilisateurs.csv",nouvelleLigne);
                utilisateurService.inscrireUtilisateur(username,password,"user");
                return "redirect:/connexion";
            }
        }else{
            System.err.println("Mot de passe Incorect : " + password + "!=" + confirm_password);
            return "redirect:/inscription";
        }

    }
    // Méthode pour ajouter l'inscription au csv
    public static void ajouterLigneCsv(String cheminFichier, String[] nouvelleLigne) {
        try (FileWriter writer = new FileWriter(cheminFichier, true)) {
            // Construire la ligne CSV
            StringBuilder ligne = new StringBuilder();
            for (int i = 0; i < nouvelleLigne.length; i++) {
                ligne.append(nouvelleLigne[i]);
                if (i < nouvelleLigne.length - 1) {
                    ligne.append(","); // Ajouter une virgule entre les colonnes
                }
            }
            ligne.append("\n"); // Ajouter un saut de ligne à la fin
            
            // Écrire dans le fichier
            writer.write(ligne.toString());
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout de la ligne au fichier CSV : " + e.getMessage());
        }
    }


}
