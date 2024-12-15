package com.historydle.demo.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Repository.PersonnageRepository;

//Cette classe gère la logique pour sélectionner un personnage comme réponse du jour pour le jeu des citations.
@Controller
public class ReponseCitationController {

    @Autowired
    private PersonnageRepository personnageRepository;

    private static Personnage reponseDuJour; 
    private static boolean isInitialized = false; // Flag pour éviter les réinitalisations

    // Retourne le personnage sélectionné comme réponse du jour.
     public Personnage getReponseDuJour() {
        if (!isInitialized) {
            genererReponseDuJour("Citation");
            isInitialized = true; // Marquer comme initialisé
        }
        return reponseDuJour;
    }

    // Génère aléatoirement un personnage comme réponse du jour en fonction d'une graine déterministe.
    private void genererReponseDuJour(String seedDuJeu) {
        List<Personnage> personnages = personnageRepository.findOnlyPersonnage();
        if (!personnages.isEmpty()) {
            LocalDate today = LocalDate.now(); // Date du jour
            int seed = (today.toString() + seedDuJeu).hashCode(); 
    
            // Initialiser le Random avec la seed
            Random random = new Random(seed);
            reponseDuJour = personnages.get(random.nextInt(personnages.size()));
            System.out.println("La réponse du jour pour Citation est : " + reponseDuJour.getNom());
        }
    }
}
