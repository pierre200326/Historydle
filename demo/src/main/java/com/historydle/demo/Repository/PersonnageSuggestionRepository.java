package com.historydle.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historydle.demo.Entity.PersonnageSuggestion;
@Repository
public interface PersonnageSuggestionRepository extends JpaRepository<PersonnageSuggestion, Long> {
}
