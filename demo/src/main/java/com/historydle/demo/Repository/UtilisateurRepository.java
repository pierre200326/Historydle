package com.historydle.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.historydle.demo.Entity.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByPseudo(String pseudo);
}
