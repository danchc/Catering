package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChefController {

    @Autowired
    private ChefService chefService;

    @Autowired
    private BuffetService buffetService;

    @GetMapping("/addChef")
    public String getChefForm(Model model){
        model.addAttribute("buffet", this.buffetService.getAllBuffet());
        return "admin/chefForm";
    }

    @PostMapping("/addChef")
    public String addNewChef(Model model,
                             @ModelAttribute("chef")Chef chef,
                             BindingResult bindingResultChef){
        //se tutto va bene puoi aggiungere i piatti
        if(!bindingResultChef.hasErrors()){
            this.chefService.save(chef);
            return "redirect:/controlpanel";
        }
        return "admin/chefForm";
    }
}
