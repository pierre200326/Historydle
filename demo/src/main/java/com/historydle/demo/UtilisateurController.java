package com.historydle.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @GetMapping("/profilUser")
    public String afficherProfilUser() {
        // Retourne le fichier HTML "profilUser.html" situé dans src/main/resources/templates/
        return "profilUser";
    }


}
