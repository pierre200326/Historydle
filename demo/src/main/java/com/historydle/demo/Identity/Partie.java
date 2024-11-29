package com.historydle.demo.Identity;

import jakarta.persistence.*;

@Entity
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jeu; // Nom du jeu
    private String personnageTrouve; // Nom du personnage trouvé

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur; // Relation avec l'utilisateur

    public Partie() {}

    public Partie(String jeu, String personnageTrouve, Utilisateur utilisateur) {
        this.jeu = jeu;
        this.personnageTrouve = personnageTrouve;
        this.utilisateur = utilisateur;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJeu() { return jeu; }
    public void setJeu(String jeu) { this.jeu = jeu; }

    public String getPersonnageTrouve() { return personnageTrouve; }
    public void setPersonnageTrouve(String personnageTrouve) { this.personnageTrouve = personnageTrouve; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
}