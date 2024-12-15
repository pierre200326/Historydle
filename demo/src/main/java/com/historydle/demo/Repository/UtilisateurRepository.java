package com.historydle.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historydle.demo.Entity.Utilisateur;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByPseudo(String pseudo); //Trouve un utilisateur avec son pseudo
}
