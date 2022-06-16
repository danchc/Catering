package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.IngredienteValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.IngredienteService;
import it.uniroma3.siw.spring.service.NazioneService;
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
public class IngredienteController {

    @Autowired
    protected PiattoService piattoService;

    @Autowired
    protected IngredienteService ingredienteService;

    @Autowired
    protected NazioneService nazioneService;

    @Autowired
    protected IngredienteValidator ingredienteValidator;

    @GetMapping("/admin/new/ingrediente")
    public String getIngredienteForm(Model model){
        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        model.addAttribute("piatti", this.piattoService.getAllPiatti());
        return "admin/ingredienteForm";
    }


    @PostMapping("/new/ingrediente")
    public String addNewIngrediente(Model model,
                               @Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
                               BindingResult bindingResultIngrediente){
        this.ingredienteValidator.validate(ingrediente, bindingResultIngrediente);
        //se tutto va bene puoi aggiungere gli ingredienti
        if(!bindingResultIngrediente.hasErrors()){
            this.ingredienteService.save(ingrediente);
            return "redirect:/admin/controlpanel";
        }
        model.addAttribute("piatti", this.piattoService.getAllPiatti());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/piattoForm";
    }

    @GetMapping("/admin/delete/ingrediente/{id}")
    public String deleteIngrediente(Model model,
                               @PathVariable("id")Long id){
        Ingrediente ingrediente = this.ingredienteService.getIngredientePerId(id).get();

        if(this.ingredienteService.eliminaIngredientePerId(ingrediente)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }

}
