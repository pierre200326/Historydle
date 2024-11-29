package com.historydle.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.historydle.demo.UtilisateurService;
import com.historydle.demo.Identity.Utilisateur;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/inscription")
    public ResponseEntity<String> inscrireUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            utilisateurService.inscrireUtilisateur(utilisateur.getPseudo(), utilisateur.getMdp());
            return ResponseEntity.ok("Utilisateur inscrit avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
