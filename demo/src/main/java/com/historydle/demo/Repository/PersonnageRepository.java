package com.historydle.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historydle.demo.Identity.Personnage;

import java.util.List;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
    List<Personnage> findByNomContainingIgnoreCase(String nom);
    Personnage findByNomIgnoreCase(String nom);
}
