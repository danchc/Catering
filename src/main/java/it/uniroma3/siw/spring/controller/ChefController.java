package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.NazioneService;
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
    private NazioneService nazioneService;


    @GetMapping("/admin/new/chef")
    public String getChefForm(Model model){
        model.addAttribute("chef", new Chef());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/chefForm";
    }

    @PostMapping("/new/chef")
    public String addNewChef(Model model,
                             @ModelAttribute("chef")Chef chef,
                             BindingResult bindingResultChef){
        //se tutto va bene puoi aggiungere i piatti
        if(!bindingResultChef.hasErrors()){
            this.chefService.save(chef);

            return "redirect:/admin/controlpanel";
        }
        return "admin/chefForm";
    }
}
