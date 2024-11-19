package com.historydle.demo;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder.In;


@Entity
public class Indice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private Personnage personnage;
    private String indice;

    public Indice (){}  
    public Indice(Personnage personnage, String indice){
        this.personnage=personnage;
        this.indice = indice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Personnage getPersonnage() { return personnage;}
    public void setPersonnage(Personnage personnage) { this.personnage=personnage;}

    public String getIndice(){ return indice;}
    public void setIndice(String Indice){ this.indice=indice;}
    
}