package com.historydle.demo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.historydle.demo.Entity.PersonnageSuggestion;
import com.historydle.demo.Repository.PersonnageSuggestionRepository;

@Service
public class SuggestionService {
    
    private final PersonnageSuggestionRepository personnageSuggestionRepository;

     public SuggestionService(PersonnageSuggestionRepository personnageSuggestionRepository) {
        this.personnageSuggestionRepository = personnageSuggestionRepository;
    }

     public PersonnageSuggestion inscrirePersonnage(String nom,String genre,String pays,String continent, String domaine, int siecle, String citation,String pseudo) {
        PersonnageSuggestion personnageSuggestion = new PersonnageSuggestion(nom,genre,pays,continent,domaine,siecle,citation,pseudo);
        return personnageSuggestionRepository.save(personnageSuggestion);
    }

    public List<PersonnageSuggestion> findAllUtilisateurs() {
        return personnageSuggestionRepository.findAll();
    }

}
