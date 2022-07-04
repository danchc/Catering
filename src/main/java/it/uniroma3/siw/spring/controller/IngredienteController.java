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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * Questo metodo ci reindirizza alla pagina per la creazione di un nuovo ingrediente.
     * @param model
     * @return admin/ingredienteForm.html
     */
    @GetMapping("/admin/new/ingrediente")
    public String getIngredienteForm(Model model){
        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        model.addAttribute("listPiatti", this.piattoService.getAllPiatti());
        return "admin/ingredienteForm";
    }


    /**
     * Questo metodo gestisce l'invio dei dati al server per quanto riguarda la creazione di
     * un nuovo ingrediente. Controlla i vari input e se non ci sono errori salva tutti
     * i dati all'interno del database.
     *
     * @param model
     * @param ingrediente
     * @param bindingResultIngrediente
     * @param id
     * @return se non ci sono errori admin/controlpanel.html, se ci sono errori
     *          admin/ingredienteForm.html
     */
    @PostMapping("/new/ingrediente")
    public String addNewIngrediente(Model model,
                               @Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
                               BindingResult bindingResultIngrediente,
                                @RequestParam(name = "piatto") Long id){
        this.ingredienteValidator.validate(ingrediente, bindingResultIngrediente);
        //se tutto va bene puoi aggiungere gli ingredienti
        if(!bindingResultIngrediente.hasErrors()){
            ingrediente.addPiatto(this.piattoService.getPiattoById(id).get());
            this.ingredienteService.save(ingrediente);
            return "redirect:/admin/controlpanel";
        }
        model.addAttribute("listPiatti", this.piattoService.getAllPiatti());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/ingredienteForm";
    }

    /**
     * Questo metodo gestisce l'eliminazione di un determinato ingrediente dal database in base
     * all'id.
     * @param model
     * @param id
     * @return se non ci sono errori allora admin/controlpanel.html
     */
    @GetMapping("/admin/delete/ingrediente/{id}")
    public String deleteIngrediente(Model model,
                               @PathVariable("id")Long id){
        Ingrediente ingrediente = this.ingredienteService.getIngredientePerId(id).get();

        if(this.ingredienteService.eliminaIngredientePerId(ingrediente)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina della modifica dei dati
     * di un determinato ingrediente.
     * @param model
     * @param id
     * @return admin/ingredienteFormUpdate.html
     */
    @GetMapping("/admin/update/ingrediente/{id}")
    public String getUpdateIngrediente(Model model,
                               @PathVariable("id") Long id){
        Ingrediente ingrediente = this.ingredienteService.getIngredientePerId(id).get();
        model.addAttribute("ingrediente", ingrediente);
        model.addAttribute("listPiatti", this.piattoService.getAllPiatti());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());

        return "admin/ingredienteFormUpdate";
    }

    /**
     * Questo metodo gestisce l'invio dei dati al server per quanto riguarda la modifica di
     * un ingrediente. Controlla i vari input e se non ci sono errori salva tutti
     * i dati all'interno del database sovrascrivendo la vecchia entità ingrendiente.
     * @param model
     * @param ingrediente
     * @param bindingResult
     * @return se non ci sono errori admin/controlpanel.html
     */
    @PostMapping("/update/ingrediente")
    private String updateIngrediente(Model model,
                               @Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
                               BindingResult bindingResult){

        this.ingredienteValidator.validate(ingrediente, bindingResult);

        if(!bindingResult.hasErrors()){
            this.ingredienteService.save(ingrediente);
            return "redirect:/admin/controlpanel";
        }

        return "admin/ingredienteFormUpdate";
    }
}
