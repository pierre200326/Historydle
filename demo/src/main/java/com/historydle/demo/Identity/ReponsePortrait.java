package com.historydle.demo.Identity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ReponsePortrait{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le personnage choisi comme réponse pour le jour
    @OneToOne
    private Personnage personnage;

    // Date de la dernière mise à jour de la réponse
    private LocalDate dateDuJour;

    // Constructeur par défaut requis par JPA
    public ReponsePortrait() {}

    public ReponsePortrait(Personnage personnage, LocalDate dateDuJour) {
        this.personnage = personnage;
        this.dateDuJour = dateDuJour;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Personnage getPersonnage() { return personnage; }
    public void setPersonnage(Personnage personnage) { this.personnage = personnage; }

    public LocalDate getDateDuJour() { return dateDuJour; }
    public void setDateDuJour(LocalDate dateDuJour) { this.dateDuJour = dateDuJour; }
}
