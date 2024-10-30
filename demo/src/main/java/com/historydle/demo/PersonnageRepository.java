package com.historydle.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface pour gérer l'accès à la base de données pour les entités Personnage.
 * En héritant de JpaRepository, on obtient des méthodes CRUD par défaut (save, findAll, etc.).
 */
@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
    // Ici, on pourrait ajouter des méthodes personnalisées pour des recherches spécifiques.
}
