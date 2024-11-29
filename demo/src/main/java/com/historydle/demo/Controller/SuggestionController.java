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

@Controller
public class SuggestionController {

    @Autowired
    private PersonnageSuggestionRepository suggestionRepository;
    
    @GetMapping("/suggestion")
    public String showSuggestionForm(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "connexion";
        }else{
            model.addAttribute("suggestion", new PersonnageSuggestion(username));
            return "suggestion";        
        }
    }

    @PostMapping("/suggestion")
    public String submitSuggestion(@ModelAttribute("suggestion") PersonnageSuggestion suggestion, HttpSession session) {
        // Récupérer le pseudo depuis la session
        String username = (String) session.getAttribute("username");
        if (username != null) {
            suggestion.setPseudoUtilisateur(username); // Associer le pseudo récupéré
        }
        suggestionRepository.save(suggestion);
        String stringPeriode = String.valueOf(suggestion.getPeriode());
        String[] nouvelleLigne = {username, suggestion.getNom(),suggestion.getGenre(),suggestion.getPays(),suggestion.getContinent(),suggestion.getDomaine(),stringPeriode,suggestion.getCitation()};
        ajouterLigneCsv("./data/suggestion.csv",nouvelleLigne);
        // Ajout d'une confirmation visuelle pour l'utilisateur
        return "redirect:/suggestion?success=true";
    }


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
