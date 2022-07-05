package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.PiattoValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Ingrediente;
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
import java.io.IOException;

@Controller
public class PiattoController {

    @Autowired
    protected BuffetService buffetService;

    @Autowired
    protected PiattoService piattoService;

    @Autowired
    protected PiattoValidator piattoValidator;

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina per la creazione di un nuovo piatto.
     * @param model
     * @return admin/piattoForm.html
     */
    @GetMapping("/admin/new/piatto")
    public String getPiattoForm(Model model) {
        model.addAttribute("piatto", new Piatto());
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        return "admin/piattoForm";
    }

    /**
     * Questo metodo gestisce l'invio dei dati al server per quanto riguarda la creazione di
     * un nuovo piatto. Controlla i vari input e se non ci sono errori salva tutti
     * i dati all'interno del database.
     * @param model
     * @param piatto
     * @param bindingResultPiatto
     * @return admin/controlpanel.html se non ci sono errori, altrimenti chefForm.html
     */
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

    /**
     * Questo metodo gestisce l'eliminazione di un determinato piatto dal database in base
     * all'id.
     * @param model
     * @param id
     * @return admin/controlpanel.html se non ci sono errori, error altrimenti
     */
    @GetMapping("/admin/delete/piatto/{id}")
    public String deletePiatto(Model model,
                               @PathVariable("id")Long id){
        Piatto piatto = this.piattoService.getPiattoById(id).get();

        if(this.piattoService.eliminaPiattoPerId(piatto)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }


    /**
     * Questo metodo gestisce il reindirizzamento alla pagina della modifica dei dati
     * di un determinato piatto in base all'id.
     * @param model
     * @param id
     * @return piattoFormUpdate.html
     */
    @GetMapping("/admin/update/piatto/{id}")
    public String updatePiatto(Model model,
                                    @PathVariable("id") Long id){
        Piatto piatto = this.piattoService.getPiattoById(id).get();
        model.addAttribute("piatto", piatto);
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());

        return "admin/piattoFormUpdate";
    }

    /**
     * Questo metodo gestisce l'aggiornamento del piatto. In particolare, gestisce l'invio
     * dei dati al server per l'aggiornamento delle informazioni del piatto.
     *
     * @param model
     * @param piatto
     * @param bindingResult
     * @return admin/controlpanel.html se non ci sono errori, piattoFormUpdate altrimenti
     */
    @PostMapping("/update/piatto")
    private String updatePiatto(Model model,
                                     @Valid @ModelAttribute("piatto") Piatto piatto,
                                     BindingResult bindingResult){

        this.piattoValidator.validate(piatto, bindingResult);

        if(!bindingResult.hasErrors()){
            this.piattoService.save(piatto);
            return "redirect:/admin/controlpanel";
        }

        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        return "admin/piattoFormUpdate";
    }

}
