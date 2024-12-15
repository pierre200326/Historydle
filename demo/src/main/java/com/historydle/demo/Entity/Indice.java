package com.historydle.demo.Entity;

import jakarta.persistence.*;

// Entité représentant un indice associé à un personnage pour le jeu
@Entity
public class Indice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; // Identifiant unique pour chaque indice
    
    @ManyToOne(fetch=FetchType.EAGER) // L'indice est associé à un personnage, plusieurs indices peuvent appartenir à un même personnage
    private Personnage personnage; // Le personnage auquel cet indice est lié
    
    private String indice; // Le texte de l'indice

    // Constructeur par défaut
    public Indice() {}

    // Constructeur avec paramètres pour initialiser un indice avec un personnage et un texte d'indice
    public Indice(Personnage personnage, String indice) {
        this.personnage = personnage; // Associe le personnage
        this.indice = indice; // Initialise le texte de l'indice
    }

    // Getter pour l'ID
    public Long getId() { return id; }

    // Setter pour l'ID
    public void setId(Long id) { this.id = id; }

    // Getter pour le personnage associé à l'indice
    public Personnage getPersonnage() { return personnage; }

    // Setter pour le personnage
    public void setPersonnage(Personnage personnage) { this.personnage = personnage; }

    // Getter pour l'indice (le texte)
    public String getIndice() { return indice; }

    // Setter pour l'indice (le texte)
    public void setIndice(String indice) { this.indice = indice; }
}
