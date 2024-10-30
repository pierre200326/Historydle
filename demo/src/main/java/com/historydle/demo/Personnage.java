package com.historydle.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Classe représentant le modèle de données pour un personnage historique.
 * Annotée avec @Entity pour indiquer que cette classe est une entité JPA,
 * donc liée à une table de la base de données.
 */
@Entity
public class Personnage {

    // Identifiant unique pour chaque personnage, généré automatiquement par la BDD.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Attributs de base : nom et domaine (ex: artiste, politicien).
    private String nom;
    private String domaine;

    // Constructeur par défaut requis par JPA.
    public Personnage() {}

    // Constructeur pour initialiser un personnage avec nom et domaine.
    public Personnage(String nom, String domaine) {
        this.nom = nom;
        this.domaine = domaine;
    }

    // Getters et setters pour chaque attribut.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }
}