package com.historydle.demo.Entity;
import jakarta.persistence.Entity;

// La classe PersonnageSuggestion hérite de la classe Personnage et ajoute un attribut pseudoUtilisateur
@Entity
public class PersonnageSuggestion extends Personnage {

    // Attribut spécifique à la classe PersonnageSuggestion : pseudo de l'utilisateur ayant suggéré le personnage
    private String pseudoUtilisateur;

    // Constructeur par défaut 
    public PersonnageSuggestion() {}

    // Constructeur avec un seul paramètre pour initialiser pseudoUtilisateur
    public PersonnageSuggestion(String pseudoUtilisateur) {
        this.pseudoUtilisateur = pseudoUtilisateur;
    }

    // Constructeur avec tous les paramètres nécessaires, utilisant le constructeur de la classe parente Personnage
    public PersonnageSuggestion(String nom, String genre, String pays, String continent, String domaine, int periode, String citation, String pseudoUtilisateur) {
        super(nom, genre, pays, continent, domaine, periode, citation); // Appel au constructeur de la classe Personnage pour initialiser ses attributs
        this.pseudoUtilisateur = pseudoUtilisateur; // Initialisation de l'attribut pseudoUtilisateur
    }

    // Getter pour l'attribut pseudoUtilisateur
    public String getPseudoUtilisateur() { 
        return pseudoUtilisateur; 
    }

    // Setter pour l'attribut pseudoUtilisateur
    public void setPseudoUtilisateur(String pseudoUtilisateur) { 
        this.pseudoUtilisateur = pseudoUtilisateur; 
    }
}
