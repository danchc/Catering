package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.PiattoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    @Autowired
    private BuffetService buffetService;

    @Autowired
    private PiattoService piattoService;

    @GetMapping("/admin/controlpanel")
    public String getControlPanel(Model model) {
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        model.addAttribute("listPiatti", this.piattoService.getAllPiatti());
        return "admin/controlpanel";
    }



}
