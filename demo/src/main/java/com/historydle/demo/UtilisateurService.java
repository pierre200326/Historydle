package com.historydle.demo;
import com.historydle.demo.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.historydle.demo.*;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

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

    public boolean existeParPseudo(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo).isPresent();
    }

    public Optional<Utilisateur> findByPseudo(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo);
    }

}
