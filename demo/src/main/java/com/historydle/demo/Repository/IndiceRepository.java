package com.historydle.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historydle.demo.Entity.Indice;

// Repository pour les indices
@Repository
public interface IndiceRepository extends JpaRepository<Indice, Long> {
   
}
