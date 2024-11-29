package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;


@Controller
public class DevinetteController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponseDevinetteController reponseDevinetteController;

    private final List<Map<String, Object>> resultats = new ArrayList<>();
    private int tourDeJeu = 0; // Initialiser le compteur de tours
    
    @GetMapping("/jouer")
    public String jouer(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        }else{
            System.out.println("Utilisateur connecté : " + username);
        }

    model.addAttribute("resultats", resultats);
    model.addAttribute("tourDeJeu", tourDeJeu);
    model.addAttribute("ageDisponibleDans", Math.max(0, 3 - tourDeJeu)); // Limiter à 0 minimum
    model.addAttribute("titreDisponibleDans", Math.max(0, 6 - tourDeJeu)); // Limiter à 0 minimum
    model.addAttribute("hasCorrectName", resultats.stream().anyMatch(resultat -> Boolean.TRUE.equals(resultat.get("nomCorrect"))));
    return "jouer";
}



    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam("query") String query) {
        List<Personnage> personnages = personnageRepository.findByNomContainingIgnoreCase(query);
        List<String> suggestions = new ArrayList<>();
        for (Personnage personnage : personnages) {
            suggestions.add(personnage.getNom());
        }
        return suggestions;
    }

    @PostMapping("/verifierReponse")
    public String verifierReponse(@RequestParam("reponse") String reponseUtilisateur, Model model) {
    // Récupérer le personnage correspondant au nom saisi par l'utilisateur
    Personnage personnageUtilisateur = personnageRepository.findByNomIgnoreCase(reponseUtilisateur);
    tourDeJeu++;
    // Récupérer la réponse du jour
    Personnage reponseDuJour = reponseDevinetteController.getReponseDuJour();
    boolean nomCorrect = false;
    List<Indice> indices = reponseDuJour.getIndices(); // Récupérer les indices du personnage
    // Créer une carte pour stocker les résultats
    Map<String, Object> resultat = new HashMap<>();

    if (personnageUtilisateur != null) {
        // Récupérer les attributs du personnage de l'utilisateur
        String nomUtilisateur = personnageUtilisateur.getNom();
        String domaineUtilisateur = personnageUtilisateur.getDomaine();
        String genreUtilisateur = personnageUtilisateur.getGenre();
        String paysUtilisateur = personnageUtilisateur.getPays();
        String continentUtilisateur = personnageUtilisateur.getContinent();
        int periodeUtilisateur = personnageUtilisateur.getPeriode();

        // Comparer avec la réponse du jour
        nomCorrect = reponseDuJour.getNom().equalsIgnoreCase(nomUtilisateur);
        boolean domaineCorrect = reponseDuJour.getDomaine().equalsIgnoreCase(domaineUtilisateur);
        boolean domainePartiellementVrai= reponseDuJour.getDomaine().contains(domaineUtilisateur) || domaineUtilisateur.contains(reponseDuJour.getDomaine());
        boolean genreCorrect = reponseDuJour.getGenre().equalsIgnoreCase(genreUtilisateur);
        boolean paysCorrect = reponseDuJour.getPays().equalsIgnoreCase(paysUtilisateur);
        boolean paysPartiellementVrai= reponseDuJour.getPays().contains(paysUtilisateur) || paysUtilisateur.contains(reponseDuJour.getPays()) ;
        boolean continentCorrect = reponseDuJour.getContinent().equalsIgnoreCase(continentUtilisateur);
        boolean continentPartiellementVrai= reponseDuJour.getContinent().contains(continentUtilisateur)|| continentUtilisateur.contains(reponseDuJour.getContinent());
        boolean periodeCorrect = reponseDuJour.getPeriode() == periodeUtilisateur;
        boolean periodePlusVieux = periodeUtilisateur < reponseDuJour.getPeriode();
        boolean periodePlusJeune = periodeUtilisateur > reponseDuJour.getPeriode();
        String indiceAge=indices.get(0).getIndice();
        String indiceTitre=indices.get(1).getIndice();


        // Ajouter les résultats à la carte
        resultat.put("nom", nomUtilisateur);
        resultat.put("domaine", domaineUtilisateur);
        resultat.put("pays", paysUtilisateur);
        resultat.put("continent", continentUtilisateur);
        resultat.put("genre", genreUtilisateur);
        resultat.put("periode", periodeUtilisateur);

        resultat.put("nomCorrect", nomCorrect);
        resultat.put("domaineCorrect", domaineCorrect);
        resultat.put("domainePartiellementVrai", domainePartiellementVrai);
        resultat.put("paysCorrect", paysCorrect);
        resultat.put("paysPartiellementVrai", paysPartiellementVrai);
        resultat.put("continentCorrect", continentCorrect);
        resultat.put("continentPartiellementVrai",continentPartiellementVrai);
        resultat.put("genreCorrect", genreCorrect);
        resultat.put("periodeCorrect", periodeCorrect);
        resultat.put("periodePlusVieux", periodePlusVieux);
        resultat.put("periodePlusJeune", periodePlusJeune);
        resultat.put("indiceAge", indiceAge);
        resultat.put("indiceTitre", indiceTitre);
       
    } else {
        // Si le personnage n'est pas trouvé, gérer ce cas
        resultat.put("nom", reponseUtilisateur);
        resultat.put("domaine", "Personnage non trouvé");
        resultat.put("nomCorrect", false);
        resultat.put("domaineCorrect", false);
        resultat.put("paysCorrect", false);
        resultat.put("continentCorrect", false);
        resultat.put("genreCorrect", false);
        resultat.put("periodeCorrect", false);
        resultat.put("periodePlusVieux", false);
        resultat.put("periodePlusJeune", false);
    }

    // Ajouter le résultat a la fin de la liste pour avoir un affichage inversé
    resultats.add(0, resultat); 
    
    // Ajouter la liste des résultats au modèle
    model.addAttribute("resultats", resultats);
    model.addAttribute("hasCorrectName", nomCorrect);

    return "redirect:/jouer";
}

}
