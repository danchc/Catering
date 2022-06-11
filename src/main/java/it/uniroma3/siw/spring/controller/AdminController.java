package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.service.BuffetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    @Autowired
    private BuffetService buffetService;

    @GetMapping("/controlpanel")
    public String getControlPanel(Model model) {
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        return "admin/controlpanel";
    }



}
