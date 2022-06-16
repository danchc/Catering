package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.PiattoValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.PiattoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PiattoController {

    @Autowired
    protected BuffetService buffetService;

    @Autowired
    protected PiattoService piattoService;

    @Autowired
    protected PiattoValidator piattoValidator;

    @GetMapping("/admin/new/piatto")
    public String getPiattoForm(Model model) {
        model.addAttribute("piatto", new Piatto());
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        return "admin/piattoForm";
    }

    @PostMapping("/new/piatto")
    public String addNewPiatto(Model model,
                             @Valid @ModelAttribute("piatto") Piatto piatto,
                             BindingResult bindingResultPiatto){
        this.piattoValidator.validate(piatto, bindingResultPiatto);
        //se tutto va bene puoi aggiungere i piatti
        if(!bindingResultPiatto.hasErrors()){
            this.piattoService.save(piatto);
            return "redirect:/admin/controlpanel";
        }
        return "admin/chefForm";
    }

    @GetMapping("/admin/delete/piatto/{id}")
    public String deletePiatto(Model model,
                               @PathVariable("id")Long id){
        Piatto piatto = this.piattoService.getPiattoById(id).get();

        if(this.piattoService.eliminaPiattoPerId(piatto)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }
}
