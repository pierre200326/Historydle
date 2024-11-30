package com.historydle.demo.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.io.UnsupportedEncodingException;
import jakarta.persistence.OneToMany;
import java.net.URLEncoder;
import java.util.*;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pseudo;
    private String mdp;
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partie> parties = new ArrayList<>(); 
    @ManyToMany
    @JoinTable(
        name = "utilisateur_personnage_like",
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "personnage_id")
    )
    private List<Personnage> personnagesLikes = new ArrayList<>();

    public Utilisateur() {}

        public Utilisateur(String pseudo, String mdp) {
            this.pseudo = pseudo;
            this.mdp = mdp;
        }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public List<Partie> getParties() { return parties; }
    public void setParties(List<Partie> parties) { this.parties = parties; }

    public boolean ajouterLike(Personnage personnage) {
        if (personnagesLikes.size() < 3) {
            personnagesLikes.add(personnage);
            personnage.getUtilisateursQuiOntLike().add(this);
            return true;
        }
        return false; // Refus d'ajouter un like si déjà 3 likes
    }
    public List<Personnage> getPersonnagesLikes() { return personnagesLikes; }
    public void setPersonnagesLikes(List<Personnage> personnagesLikes) { this.personnagesLikes = personnagesLikes; }
}

