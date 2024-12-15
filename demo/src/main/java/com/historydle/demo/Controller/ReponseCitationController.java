package com.historydle.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.historydle.demo.Entity.Personnage;
import com.historydle.demo.Repository.PersonnageRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class ReponseCitationController {

    @Autowired
    private PersonnageRepository personnageRepository;

    private static Personnage reponseDuJour; // Changer à static
    private static boolean isInitialized = false; // Flag pour éviter les réinitalisations

     public Personnage getReponseDuJour() {
        if (!isInitialized) {
            genererReponseDuJour("Citation");
            isInitialized = true; // Marquer comme initialisé
        }
        return reponseDuJour;
    }

    private void genererReponseDuJour(String seedDuJeu) {
        List<Personnage> personnages = personnageRepository.findAll();
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
