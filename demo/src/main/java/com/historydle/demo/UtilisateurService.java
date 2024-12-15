package com.historydle.demo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.Repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    // Initialisation de la classe
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    //Inscrit un utilisateur
    public Utilisateur inscrireUtilisateur(String pseudo, String mdp, String statut) {
        // Vérifiez si le pseudo existe déjà
        if (utilisateurRepository.findByPseudo(pseudo).isPresent()) {
            throw new IllegalArgumentException("Le pseudo est déjà utilisé !");
        }

        // Créez un nouveau utilisateur
        Utilisateur utilisateur = new Utilisateur(pseudo, mdp, statut);
        return utilisateurRepository.save(utilisateur);
    }

    // Méthode pour authentifier l'utilisateur sans hachage
    public boolean authenticate(String username, String password) {
        // Recherche de l'utilisateur par son pseudo
        Optional<Utilisateur> optionalUser = utilisateurRepository.findByPseudo(username);
        
        // Si l'utilisateur existe
        if (optionalUser.isPresent()) {
            Utilisateur user = optionalUser.get();
            
            // Comparer le mot de passe en clair avec celui de la base de données
            return password.equals(user.getMdp());
        }
        
        // Si l'utilisateur n'est pas trouvé, l'authentification échoue
        return false;
    }
    //Cherche si un pseudo existe
    public boolean existeParPseudo(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo).isPresent();
    }
    //Cherche un utilisateur avec son pseudo
    public Optional<Utilisateur> findByPseudo(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo);
    }
    // Renvoi la liste de tous les utilisateurs
    public List<Utilisateur> findAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    // Supprime un utilisateur grâce à son pseudo
    public boolean supprimerUtilisateur(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo).map(utilisateur -> {
            utilisateurRepository.delete(utilisateur);
            return true;
        }).orElse(false);
    }
    // Modifie un utilisateur
    public void modifierUtilisateur(Long id, String nouveauPseudo, String nouveauMotDePasse) {
        utilisateurRepository.findById(id).ifPresent(utilisateur -> {
            utilisateur.setPseudo(nouveauPseudo);
            utilisateur.setMdp(nouveauMotDePasse);
            utilisateurRepository.save(utilisateur);
        });
    }
    // Rempli le csv utilisateur
    public void ecrireUtilisateursCsv() {
        List<Utilisateur> utilisateurs = findAllUtilisateurs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/utilisateurs.csv"))) {
            // Écrire l'en-tête CSV
            writer.write("pseudo,mdp,statut\n");

            // Écrire les données des utilisateurs
            for (Utilisateur utilisateur : utilisateurs) {
                writer.write(utilisateur.getPseudo() + "," + utilisateur.getMdp() + "," + utilisateur.getStatut() + "\n");
            }

        } catch (IOException e) {
            System.err.println("Erreur d'écriture du fichier CSV : " + e.getMessage());
        }
    }

}
