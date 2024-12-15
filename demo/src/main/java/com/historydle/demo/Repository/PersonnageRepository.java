package com.historydle.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.historydle.demo.Entity.Personnage;
// Repository pour les personnages
@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
    @Query("SELECT p FROM Personnage p WHERE TYPE(p) = Personnage AND LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Personnage> findByNomContainingIgnoreCase(@Param("nom") String nom);
    Personnage findByNomIgnoreCase(String nom);
    @Query("SELECT p FROM Personnage p WHERE TYPE(p) = Personnage")
    List<Personnage> findOnlyPersonnage();
    
}
