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
public class ReponseDevinetteController {

    @Autowired
    private PersonnageRepository personnageRepository;  //Accés aux données des personnages

    private static Personnage reponseDuJour; // Accés à la réponse du jour

    private static boolean isInitialized = false; 

    // Méthode publique pour obtenir la réponse du jour
    public Personnage getReponseDuJour() {
        // Si la réponse du jour n'est pas encore initialisée, on la génère
        if (!isInitialized) {
            genererReponseDuJour("Historydle");
            isInitialized = true; // Marque comme initialisé pour éviter de régénérer la réponse
        }
        return reponseDuJour; // Retourne le personnage à deviner pour le jour actuel
    }

    // Méthode privée pour générer la réponse du jour en fonction de la date et d'un "seed" de jeu
    private void genererReponseDuJour(String seedDuJeu) {
        // Récupère tous les personnages depuis le repository
        List<Personnage> personnages = personnageRepository.findOnlyPersonnage();
        if (!personnages.isEmpty()) {
            // Récupère la date actuelle
            LocalDate today = LocalDate.now(); 
            
            // Crée un "seed" unique pour le générateur de nombres aléatoires basé sur la date et le nom du jeu
            int seed = (today.toString() + seedDuJeu).hashCode(); 
    
            // Initialise le générateur de nombres aléatoires avec le "seed"
            Random random = new Random(seed);
            
            // Sélectionne aléatoirement un personnage parmi la liste de personnages
            reponseDuJour = personnages.get(random.nextInt(personnages.size())); 
            
            // Affiche la réponse du jour pour des fins de débogage
            System.out.println("La réponse du jour pour Devinette est : " + reponseDuJour.getNom() + ", Domaine: " + reponseDuJour.getDomaine());
        }
    }
}
