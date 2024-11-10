package com.historydle.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
    List<Personnage> findByNomStartingWithIgnoreCase(String nom);
    Personnage findByNomIgnoreCase(String nom);
}
