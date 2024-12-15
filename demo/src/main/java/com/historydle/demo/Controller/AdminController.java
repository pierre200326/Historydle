package com.historydle.demo.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String accueil(HttpSession session) {
        String admin = (String) session.getAttribute("admin");
        if (admin == null) {
            return "connexion";
        }
        return "admin";
    }
}
