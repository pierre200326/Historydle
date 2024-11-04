package com.historydle.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final PersonnageRepository personnageRepository;
    private final ReponseController reponseController;

    public DataInitializer(PersonnageRepository personnageRepository, ReponseController reponseController) {
        this.personnageRepository = personnageRepository;
        this.reponseController = reponseController;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add characters to the database if empty
        if (personnageRepository.count() == 0) {
            personnageRepository.save(new Personnage("Napoléon Bonaparte", "Homme", "France", "Europe", "Politicien", "18e-19e siècle"));
            personnageRepository.save(new Personnage("Leonardo da Vinci", "Homme", "Italie", "Europe", "Artiste", "15e-16e siècle"));
            personnageRepository.save(new Personnage("Cléopâtre", "Femme", "Égypte", "Afrique", "Reine", "1er siècle av. J.-C."));
            personnageRepository.save(new Personnage("Galilée", "Homme", "Italie", "Europe", "Scientifique", "16e-17e siècle"));
            personnageRepository.save(new Personnage("Mozart", "Homme", "Autriche", "Europe", "Musicien", "18e siècle"));
            personnageRepository.save(new Personnage("Einstein", "Homme", "Allemagne", "Europe", "Scientifique", "19e-20e siècle"));
            logger.info("Les personnages ont été ajoutés à la base de données.");
        } else {
            logger.info("La base de données contient déjà des personnages.");
        }

        personnageRepository.findAll().forEach(p -> System.out.println("Personnage : " + p.getNom()));

        // Display today's answer in the console
        Personnage reponseDuJour = reponseController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponseDuJour.getNom() + ", Domaine: " + reponseDuJour.getDomaine() + ", Genre: " + reponseDuJour.getGenre());
    }
}
