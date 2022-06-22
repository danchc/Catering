package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    @Autowired
    protected BuffetService buffetService;

    @Autowired
    protected PiattoService piattoService;

    @Autowired
    protected ChefService chefService;

    @Autowired
    protected IngredienteService ingredienteService;

    @Autowired
    protected CredentialsService credentialsService;

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina del pannello di controllo dell'amministratore
     * della pagina.
     * @param model il modello della pagina, può contenere attributi
     * @return admin/controlpanel, il pannello di controllo
     */
    @GetMapping("/admin/controlpanel")
    public String getControlPanel(Model model) {
        model.addAttribute("listChef", this.chefService.getAllChef());
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        model.addAttribute("listPiatti", this.piattoService.getAllPiatti());
        model.addAttribute("listIngredienti", this.ingredienteService.getAllIngredients());
        model.addAttribute("listUser", this.credentialsService.findAll());
        return "admin/controlpanel";
    }



}
