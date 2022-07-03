package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.NazioneService;
import it.uniroma3.siw.upload.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Controller
public class ChefController {

    @Autowired
    protected ChefService chefService;

    @Autowired
    protected NazioneService nazioneService;

    @Autowired
    protected ChefValidator chefValidator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Questo metodo gestisce il rendirizzamento alla pagina dove è possibile inserire un nuovo
     * chef.
     * @param model il modello della pagina, può contenere attributi
     * @return il form per inserire un nuovo chef
     */
    @GetMapping("/admin/new/chef")
    public String getChefForm(Model model){
        model.addAttribute("chef", new Chef());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/chefForm";
    }

    /**
     * Questo metodo gestisce la vera creazione dell'oggetto chef inviando i dati al server.
     * @param model il modello della pagina, può contenere attributi
     * @param chef, l'oggetto chef appena creato
     * @param bindingResultChef, necessario per salvare eventuali errori
     * @return se non ci sono errori, ci reindirizza al pannello di controllo. se ci sono errori invece
     * ci reindirizza allo stesso form avvisandoci degli errori presenti.
     * @throws IOException
     */
    @PostMapping("/new/chef")
    public String addNewChef(Model model,
                             @Valid @ModelAttribute("chef")Chef chef,
                             BindingResult bindingResultChef){
        this.chefValidator.validate(chef, bindingResultChef);
        //se tutto va bene puoi aggiungere i piatti
        if(!bindingResultChef.hasErrors()){
            this.chefService.save(chef);

            return "redirect:/admin/controlpanel";
        }
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/chefForm";
    }

    /**
     * Questo metodo gestisce l'eliminazione di un determinato chef.
     * @param model il modello della pagina, può contenere attributi
     * @param id, l'identificatore (id) dello chef selezionato
     * @return se non ci sono errori, la pagina del pannello di controllo
     */
    @GetMapping("/admin/delete/chef/{id}")
    public String deleteChef(Model model,
                               @PathVariable("id")Long id){
        Chef chef = this.chefService.getChefById(id).get();

        if(this.chefService.eliminaChefPerId(chef)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }

    /**
     * Questo metodo gestisce l'aggiornamento dello chef.
     * @param model il modello della pagina, può contenere attributi
     * @param id, l'identificatore (id) dello chef selezionato
     * @return il form per aggiornare lo chef selezionato
     */
    @GetMapping("/admin/update/chef/{id}")
    public String updateChef(Model model,
                               @PathVariable("id") Long id){
        logger.debug("### updating chef... ###");
        Chef chef = this.chefService.getChefById(id).get();
        model.addAttribute("chef", chef);
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/chefFormUpdate";
    }

    /**
     * Questo metodo gestisce l'invio dei dati al server una volta aggiornato lo chef selezionato.
     *
     * @param model il modello della pagina, può contenere attributi
     * @param chef, l'oggetto chef selezionato
     * @param bindingResultChef, necessario per eventuali errori
     * @return se non ci sono errori il pannello di controllo sennò il form per l'aggiornamento
     * dello chef con gli errori
     * @throws IOException
     */
    @PostMapping("/update/chef")
    private String updateChef(Model model,
                               @ModelAttribute("chef") Chef chef,
                               BindingResult bindingResultChef) throws IOException {

        this.chefValidator.validate(chef, bindingResultChef);
        /*
            Normal use
         */
        if(!bindingResultChef.hasErrors()){
            this.chefService.save(chef);
            return "redirect:/admin/controlpanel";
        }

        return "admin/chefFormUpdate";
    }

    @GetMapping("/chef/{id}")
    public String getChefInfo(@PathVariable("id") Long id, Model model) {
        Chef chef = this.chefService.getChefById(id).get();
        model.addAttribute("chef", chef);
        return "chef";
    }

}
