package com.historydle.demo.Controller;

import com.historydle.demo.Entity.PersonnageSuggestion;
import com.historydle.demo.Repository.PersonnageSuggestionRepository;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

// Controller pour gérer la suggestion des personnages 
@Controller
public class SuggestionController {

    @Autowired
    private PersonnageSuggestionRepository suggestionRepository;
    
    // Méthode GET pour afficher le formulaire de suggestion
    @GetMapping("/suggestion")
    public String showSuggestionForm(Model model, HttpSession session) {

        // Récupérer le nom d'utilisateur depuis la session (si disponible)
        String username = (String) session.getAttribute("username");
        
        // Si l'utilisateur n'est pas connecté, on le redirige vers la page de connexion
        if (username == null) {
            return "connexion";
        } else {
            // Si l'utilisateur est connecté, on crée un objet suggestion et on l'ajoute au modèle
            model.addAttribute("suggestion", new PersonnageSuggestion(username));
            return "suggestion"; // Affiche la page du formulaire de suggestion
        }
    }

    // Méthode POST pour soumettre une suggestion de personnage
    @PostMapping("/suggestion")
    public String submitSuggestion(@ModelAttribute("suggestion") PersonnageSuggestion suggestion, HttpSession session) {
        // Récupérer le pseudo de l'utilisateur depuis la session
        String username = (String) session.getAttribute("username");
        
        // Si le pseudo est présent, on l'associe à la suggestion
        if (username != null) {
            suggestion.setPseudoUtilisateur(username); // Associer le pseudo récupéré à l'objet suggestion
        }
        
        // Sauvegarde de la suggestion dans la base de données via le repository
        suggestionRepository.save(suggestion);
        
        // Convertir la période de la suggestion en chaîne de caractères
        String stringPeriode = String.valueOf(suggestion.getPeriode());
        
        // Préparer les données à ajouter au fichier CSV
        String[] nouvelleLigne = {username, suggestion.getNom(), suggestion.getGenre(), suggestion.getPays(),
                                   suggestion.getContinent(), suggestion.getDomaine(), stringPeriode, suggestion.getCitation()};
        
        // Appeler la méthode pour ajouter la ligne au fichier CSV
        ajouterLigneCsv("./data/suggestion.csv", nouvelleLigne);
        
        // Rediriger l'utilisateur vers la page de suggestion avec un paramètre de succès
        return "redirect:/suggestion?success=true";
    }

    // Méthode statique pour ajouter une ligne au fichier CSV
    public static void ajouterLigneCsv(String cheminFichier, String[] nouvelleLigne) {
        try (FileWriter writer = new FileWriter(cheminFichier, true)) {
            // Construire la ligne CSV en concatenant les valeurs avec des virgules
            StringBuilder ligne = new StringBuilder();
            for (int i = 0; i < nouvelleLigne.length; i++) {
                ligne.append(nouvelleLigne[i]);
                if (i < nouvelleLigne.length - 1) {
                    ligne.append(","); // Ajouter une virgule entre les colonnes
                }
            }
            ligne.append("\n"); // Ajouter un saut de ligne à la fin
            
            // Écrire la ligne construite dans le fichier CSV
            writer.write(ligne.toString());
        } catch (IOException e) {
            // En cas d'erreur, afficher un message d'erreur
            System.err.println("Erreur lors de l'ajout de la ligne au fichier CSV : " + e.getMessage());
        }
    }
}
