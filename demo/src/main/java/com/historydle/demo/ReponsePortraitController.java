package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class ReponsePortraitController {

    @Autowired
    private PersonnageRepository personnageRepository;

    private static Personnage reponseDuJour; // Changer à static
    private static boolean isInitialized = false; // Flag pour éviter les réinitalisations

    public Personnage getReponseDuJour() {
        if (!isInitialized) {
            genererReponseDuJour("Portrait");
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
            System.out.println("La réponse du jour pour Portrait est : " + reponseDuJour.getNom());
        }
    }
}
