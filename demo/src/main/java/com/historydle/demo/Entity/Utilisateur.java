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

}

