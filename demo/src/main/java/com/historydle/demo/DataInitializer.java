package com.historydle.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final PersonnageRepository personnageRepository;

    public DataInitializer(PersonnageRepository personnageRepository) {
        this.personnageRepository = personnageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ajoute des personnages en base de données si la base est vide
        if (personnageRepository.count() == 0) {
            personnageRepository.save(new Personnage("Napoléon Bonaparte", "Politicien"));
            personnageRepository.save(new Personnage("Leonardo da Vinci", "Artiste"));
            personnageRepository.save(new Personnage("Cléopâtre", "Reine"));
            personnageRepository.save(new Personnage("Galilée", "Scientifique"));
            personnageRepository.save(new Personnage("Mozart", "Musicien"));
            personnageRepository.save(new Personnage("Einstein", "Scientifique"));
            logger.info("Les personnages ont été ajoutés à la base de données.");
        } else {
            logger.info("La base de données contient déjà des personnages.");
        }
        personnageRepository.findAll().forEach(p -> System.out.println("Personnage : " + p.getNom()));
    }
}
