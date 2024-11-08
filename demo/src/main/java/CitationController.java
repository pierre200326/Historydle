package com.historydle.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CitationController {

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ReponseController reponseController;

    private final List<Map<String, Object>> resultats = new ArrayList<>();

    @GetMapping("/citation")
    public String citation(Model model) {
        model.addAttribute("resultats", resultats);
        return "citation";
    }


    

}
