package com.historydle.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.historydle.demo.Entity.Utilisateur;
import com.historydle.demo.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    // Inscrit un utilisateur
    @PostMapping("/inscription")
    public ResponseEntity<String> inscrireUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            utilisateurService.inscrireUtilisateur(utilisateur.getPseudo(), utilisateur.getMdp(), "user");
            return ResponseEntity.ok("Utilisateur inscrit avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
