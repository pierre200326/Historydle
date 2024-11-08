package com.historydle.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Entity
public class Personnage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String genre;
    private String pays;
    private String continent;
    private String domaine;
    private String periode;
    private String imageUrl; // New attribute for storing image URL
    public String citation;

    public Personnage() {}

        public Personnage(String nom, String genre, String pays, String continent, String domaine, String periode, String citation) {
            this.nom = nom;
            this.genre = genre;
            this.pays = pays;
            this.continent = continent;
            this.domaine = domaine;
            this.periode = periode;
            this.imageUrl = "/Historydle/" + nom + ".webp"; // Remplacer les espaces par des underscores
            this.citation=citation;
        }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }

    public String getPeriode() { return periode;}
    public void setPeriode(String periode) {this.periode=periode;}

    public String getCitation() { return citation;}
    public void setCitation(String citation) {this.citation=citation;}
}

