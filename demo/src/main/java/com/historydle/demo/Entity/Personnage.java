package com.historydle.demo.Entity;

import java.util.*; 
import jakarta.persistence.*;

// Entité représentant un personnage du jeu
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Stratégie d'héritage, chaque sous-classe a sa propre table
public class Personnage {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) 
    private Long id; // Identifiant unique pour chaque personnage
    
    private String nom; // Nom du personnage
    private String genre; // Genre du personnage (par exemple, homme, femme)
    private String pays; // Pays d'origine du personnage
    private String continent; // Continent d'origine du personnage
    private String domaine; // Domaine d'activité ou spécialité du personnage
    private int periode; // Période historique du personnage (par exemple, un siècle)
    
    private String imageUrl; // URL de l'image du personnage
    public String citation; // Citation associée au personnage (utilisée pour les indices)

    // Relation One-to-Many avec la classe Indice : un personnage peut avoir plusieurs indices
    @OneToMany(mappedBy = "personnage", fetch = FetchType.EAGER)
    private List<Indice> indices = new ArrayList<>();

    // Relation Many-to-Many avec la classe Utilisateur : plusieurs utilisateurs peuvent aimer un personnage
    @ManyToMany(mappedBy = "personnagesLikes", fetch = FetchType.EAGER)
    private List<Utilisateur> utilisateursQuiOntLike = new ArrayList<>();

    // Constructeur par défaut nécessaire pour la persistance JPA
    public Personnage() {}

    // Constructeur avec paramètres pour initialiser un personnage avec ses attributs
    public Personnage(String nom, String genre, String pays, String continent, String domaine, int periode, String citation) {
        this.nom = nom;
        this.genre = genre;
        this.pays = pays;
        this.continent = continent;
        this.domaine = domaine;
        this.periode = periode;
        this.imageUrl = "/Historydle/" + nom + ".webp"; // Génère l'URL de l'image du personnage
        this.citation = citation;
    }

    // Getters et setters pour chaque attribut
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

    public int getPeriode() { return periode; }
    public void setPeriode(int periode) { this.periode = periode; }

    public String getCitation() { return citation; }
    public void setCitation(String citation) { this.citation = citation; }

    // Getters et setters pour la relation avec les indices
    public List<Indice> getIndices() { return indices; }
    public void setIndices(List<Indice> indices) { this.indices = indices; }

    // Méthode pour ajouter un indice au personnage
    public void addIndice(Indice indice) {
        this.indices.add(indice); 
        indice.setPersonnage(this);  // Assure que l'indice référence ce personnage
    }

    // Getters et setters pour la relation avec les utilisateurs qui aiment ce personnage
    public List<Utilisateur> getUtilisateursQuiOntLike() { return utilisateursQuiOntLike; }
    public void setUtilisateursQuiOntLike(List<Utilisateur> utilisateursQuiOntLike) { this.utilisateursQuiOntLike = utilisateursQuiOntLike; }
}
