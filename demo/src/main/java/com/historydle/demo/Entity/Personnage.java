package com.historydle.demo.Entity;

import java.util.*;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Personnage {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nom;
    private String genre;
    private String pays;
    private String continent;
    private String domaine;
    private int periode;
    private String imageUrl; // New attribute for storing image URL
    public String citation;

    @OneToMany(mappedBy = "personnage",fetch = FetchType.EAGER)
    private List<Indice> indices = new ArrayList<>();

    @ManyToMany(mappedBy = "personnagesLikes",fetch = FetchType.EAGER)
    private List<Utilisateur> utilisateursQuiOntLike = new ArrayList<>();

    public Personnage() {}

        public Personnage(String nom, String genre, String pays, String continent, String domaine, int periode, String citation) {
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

    public String getImage() { return imageUrl; }


    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }

    public int getPeriode() { return periode;}
    public void setPeriode(int periode) {this.periode=periode;}

    public String getCitation() { return citation;}
    public void setCitation(String citation) {this.citation=citation;}

    // Getters and setters for indices
    public List<Indice> getIndices() { return indices; }
    public void setIndices(List<Indice> indices) { this.indices = indices; }
        public void addIndice(Indice indice) {
        this.indices.add(indice);
        indice.setPersonnage(this);  // Assurez-vous que l'indice fait référence à ce personnage
        }
    public List<Utilisateur> getUtilisateursQuiOntLike() { return utilisateursQuiOntLike; }
    public void setUtilisateursQuiOntLike(List<Utilisateur> utilisateursQuiOntLike) { this.utilisateursQuiOntLike = utilisateursQuiOntLike; }
}

