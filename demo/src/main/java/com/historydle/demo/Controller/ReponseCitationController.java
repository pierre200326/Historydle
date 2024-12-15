package com.historydle.demo.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Repository.PersonnageRepository;

// Controller pour gérer la réponse du jour pour le jeu de type "Citation"
@Controller
public class ReponseCitationController {

    @Autowired
    private PersonnageRepository personnageRepository;  // Accès au repository pour récupérer les personnages

    private static Personnage reponseDuJour; // Réponse du jour, personnage à deviner
    private static boolean isInitialized = false;

    // Méthode publique pour obtenir la réponse du jour
    public Personnage getReponseDuJour() {
        // Si la réponse du jour n'a pas encore été générée, on la crée
        if (!isInitialized) {
            genererReponseDuJour("Citation"); // Génère la réponse en fonction du seed du jeu
            isInitialized = true; // Marque comme initialisé pour éviter une nouvelle génération
        }
        return reponseDuJour; // Retourne le personnage à deviner pour le jour actuel
    }

    // Méthode privée pour générer la réponse du jour en fonction de la date et du seed spécifique au jeu
    private void genererReponseDuJour(String seedDuJeu) {
        // Récupère tous les personnages à partir du repository
        List<Personnage> personnages = personnageRepository.findOnlyPersonnage();
        
        // Si la liste de personnages n'est pas vide, on peut procéder à la sélection
        if (!personnages.isEmpty()) {
            LocalDate today = LocalDate.now(); // Récupère la date actuelle

            int seed = (today.toString() + seedDuJeu).hashCode(); 

            Random random = new Random(seed);

            // Sélectionne aléatoirement un personnage dans la liste
            reponseDuJour = personnages.get(random.nextInt(personnages.size()));

            // Affiche la réponse du jour pour des fins de débogage
            System.out.println("La réponse du jour pour Citation est : " + reponseDuJour.getNom());
        }
    }
}
