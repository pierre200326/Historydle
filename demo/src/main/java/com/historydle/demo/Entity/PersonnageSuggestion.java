package com.historydle.demo.Entity;
import jakarta.persistence.Entity;


@Entity

public class PersonnageSuggestion extends Personnage {


    private String pseudoUtilisateur;

    public PersonnageSuggestion(){};
    public PersonnageSuggestion(String pseudoUtilisateur){this.pseudoUtilisateur=pseudoUtilisateur;}
    public PersonnageSuggestion(String nom, String genre, String pays, String continent, String domaine, int periode, String citation, String pseudoUtilisateur){
        super(nom,genre,pays,continent,domaine,periode,citation);
        this.pseudoUtilisateur=pseudoUtilisateur;
    }


    public String getPseudoUtilisateur() { return pseudoUtilisateur;}
    public void setPseudoUtilisateur(String pseudoUtilisateur) {this.pseudoUtilisateur=pseudoUtilisateur;}
    
}
