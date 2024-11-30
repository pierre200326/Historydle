package com.historydle.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historydle.demo.Entity.Partie;
import com.historydle.demo.Entity.Utilisateur;

import java.util.List;
@Repository
public interface PartieRepository extends JpaRepository<Partie, Long> {
    List<Partie> findByUtilisateur(Utilisateur utilisateur);
}