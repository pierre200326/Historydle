package com.historydle.demo;

public class SuggestionDto {
    private String suggestionNom;
    private String suggestionGenre; // "Homme" ou "Femme"
    private String suggestionPays;
    private String suggestionContinent;
    private String suggestionDomaine; // Politique, Art, Science, etc.
    private int suggestionPeriode;
    private String suggestionCitation;
    private String suggestionIndice1; // Âge de mort
    private String suggestionIndice2; // Titre honorifique

    public String getSuggestionNom() { return suggestionNom; }
    public void setSuggestionNom(String suggestionNom) { this.suggestionNom = suggestionNom; }

    public String getSuggestionDomaine() { return suggestionDomaine; }
    public void SetSuggestionDomaine(String suggestionDomaine) { this.suggestionDomaine = suggestionDomaine; }

    public String getSuggestionGenre() { return suggestionGenre; }
    public void setSuggestionGenre(String suggestionGenre) { this.suggestionGenre = suggestionGenre; }

    public String getSuggestionPays() { return suggestionPays; }
    public void setSuggestionPays(String suggestionPays) { this.suggestionPays = suggestionPays; }

    public String getSuggestionContinent() { return suggestionContinent; }
    public void setSuggestionContinent(String suggestionContinent) { this.suggestionContinent = suggestionContinent; }

    public int getSuggestionPeriode() { return suggestionPeriode;}
    public void setSuggestionPeriode(int suggestionPeriode) {this.suggestionPeriode=suggestionPeriode;}

    public String getSuggestionCitation() { return suggestionCitation;}
    public void setSuggestionCitation(String suggestionCitation) {this.suggestionCitation=suggestionCitation;}

    public String getSuggestionIndice1() { return suggestionIndice1;}
    public void setSuggestionIndice1(String suggestionIndice1) {this.suggestionIndice1=suggestionIndice1;}

    public String getSuggestionIndice2() { return suggestionIndice2;}
    public void setSuggestionIndice2(String suggestionIndice2) {this.suggestionIndice2=suggestionIndice2;}

}

