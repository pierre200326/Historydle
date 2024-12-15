package com.historydle.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.historydle.demo.Entity.Partie;
import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.PartieRepository;
import com.historydle.demo.Repository.PersonnageRepository;
import com.historydle.demo.Repository.UtilisateurRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

@Controller
public class PortraitController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponsePortraitController reponsePortraitController;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PartieRepository partieRepository;

    private final List<Map<String, Object>> resultats = new ArrayList<>();


     @GetMapping("/portrait")
    public String portrait(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        }else{
            System.out.println("Utilisateur connecté : " + username);
        }

        // Si un nouvel utilisateur se connecte, réinitialiser les données du jeu
        if (username != null && session.getAttribute("newUser3") == null) {
        // Initialiser ou réinitialiser les données de jeu
        resultats.clear();
        session.setAttribute("newUser3", "true");
        }

        model.addAttribute("resultats",resultats);
        Personnage reponseDuJour = reponsePortraitController.getReponseDuJour();
        if (reponseDuJour!= null){
            model.addAttribute("portrait", reponseDuJour.getNom());
        }
        else{
            model.addAttribute("portrait", "Pas de portrait disponible pour aujourd'hui");
        }
        model.addAttribute("hasCorrectName", resultats.stream().anyMatch(resultat -> Boolean.TRUE.equals(resultat.get("nomCorrect"))));


        return "portrait";
    }


    @GetMapping("/autocompletePortrait")
    @ResponseBody
    public List<String> autocompletePortrait(@RequestParam("query") String query) {
        List<Personnage> personnages = personnageRepository.findByNomContainingIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());
        }
        return suggestions;
    }

    @PostMapping("/verifierReponsePortrait")
    public String verifierReponsePortrait(@RequestParam("reponse") String reponseUtilisateur, Model model, HttpSession session) {
    // Récupérer l'utilisateur connecté, s'il existe
    String pseudoUtilisateurConnecte = (String) session.getAttribute("username");
    Utilisateur utilisateur = null;
    if (pseudoUtilisateurConnecte != null) {
        utilisateur = utilisateurRepository.findByPseudo(pseudoUtilisateurConnecte)
                           .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // Récupérer le personnage correspondant au nom saisi par l'utilisateur
    Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);

    // Récupérer la réponse du jour
    Personnage reponseDuJour = reponsePortraitController.getReponseDuJour();

    // Créer une carte pour stocker les résultats
    Map<String, Object> resultat = new HashMap<>();
    boolean nomCorrect = false;

    if (personnageUtilisateur != null) {
        // Récupérer les attributs du personnage guess
        String nomUtilisateur = personnageUtilisateur.getNom();

        // Comparer avec la réponse du jour
        nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);

        // Ajouter les résultats à la carte
        resultat.put("nom", nomUtilisateur);
        resultat.put("nomCorrect", nomCorrect);
    } else {
        // Si le personnage n'est pas trouvé, gérer ce cas
        resultat.put("nom", reponseUtilisateur);
        resultat.put("nomCorrect", false);
    }

    if (nomCorrect && utilisateur != null) {
        // Si le joueur est connecté et a deviné correctement, enregistrer la partie
        Partie nouvellePartie = new Partie("Portrait", personnageUtilisateur.getNom(), utilisateur);
        partieRepository.save(nouvellePartie);

        // Enregistrer dans un fichier CSV
        try (FileWriter csvWriter = new FileWriter("historique.csv", true)) {
            csvWriter.append(utilisateur.getPseudo());
            csvWriter.append(",");
            csvWriter.append(personnageUtilisateur.getNom());
            csvWriter.append(",");
            csvWriter.append("Portrait");
            csvWriter.append(",");
            csvWriter.append(java.time.LocalDate.now().toString()); // Date de l'enregistrement
            csvWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ajouter le résultat à la liste des résultats
    resultats.add(0, resultat);
    model.addAttribute("resultats", resultats);
    model.addAttribute("hasCorrectName", nomCorrect);

    return "redirect:/portrait";
}


}

