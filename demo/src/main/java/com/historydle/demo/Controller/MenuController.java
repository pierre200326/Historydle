package com.historydle.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;


@Controller
public class MenuController {

    @GetMapping("/")
    public String accueil(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("Aucun utilisateur n'est connecté");
        }else{
            System.out.println("Utilisateur connecté : " + username);
        }
        return "index";
    }

}
