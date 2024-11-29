package com.historydle.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.historydle.demo.Identity.Partie;
import com.historydle.demo.Identity.Utilisateur;

import java.util.List;

public interface PartieRepository extends JpaRepository<Partie, Long> {
    List<Partie> findByUtilisateur(Utilisateur utilisateur);
}