package com.historydle.demo;

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
    private String statut;

    public Utilisateur() {}

        public Utilisateur(String pseudo, String mdp, String statut) {
            this.pseudo = pseudo;
            this.mdp = mdp;
            this.statut = statut;
        }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

}

