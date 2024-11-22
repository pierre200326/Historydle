package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
            genererReponseDuJour();
            isInitialized = true; // Marquer comme initialisé
        }
        return reponseDuJour;
    }

    private void genererReponseDuJour() {
        List<Personnage> personnages = personnageRepository.findAll();
        if (!personnages.isEmpty()) {
            reponseDuJour = personnages.get(new Random().nextInt(personnages.size()));
            System.out.println("La réponse du jour pour Portrait est : " + reponseDuJour.getNom() + ", Domaine: " + reponseDuJour.getDomaine());
        }
    }
}
