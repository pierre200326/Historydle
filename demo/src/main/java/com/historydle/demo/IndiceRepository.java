package com.historydle.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndiceRepository extends JpaRepository<Indice, Long> {
   
}
