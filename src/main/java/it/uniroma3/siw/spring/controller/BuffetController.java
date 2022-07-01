package it.uniroma3.siw.spring.controller;


import it.uniroma3.siw.spring.controller.validator.BuffetValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.*;
import it.uniroma3.siw.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;


@Controller
public class BuffetController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected BuffetService buffetService;

    @Autowired
    protected ChefService chefService;

    @Autowired
    protected BuffetValidator buffetValidator;

    @Autowired
    protected TipologiaService tipologiaService;

    @Autowired
    protected AWSS3Service awss3Service;

    /**
     * Questo metodo ci reindirizza alla pagina dove sono presenti tutti i buffets inseriti
     * dall'amministratore del sito.
     * @param session la sessione corrente
     * @param model il modello della pagina, può contenere attributi
     * @return la pagina buffets.html
     */
    @GetMapping("/buffets")
    public String getBuffets(HttpSession session, Model model) {
        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        return "buffets";
    }

    /**
     * Questo metodo gestisce il rendirizzamento alla pagina dove è possibile inserire un nuovo
     * buffet.
     * @param model il modello della pagina, può contenere attributi
     * @return il form per inserire un nuovo buffet
     */
    @GetMapping("/admin/new/buffet")
    public String getBuffetForm(Model model) {
        model.addAttribute("buffet", new Buffet());
        model.addAttribute("listChef", this.chefService.getAllChef());
        model.addAttribute("listTipologia", this.tipologiaService.getAllTipologie());
        return "admin/buffetForm";
    }

    /**
     * Questo metodo gestisce la vera creazione dell'oggetto buffet inviando i dati al server.
     * @param model il modello della pagina, può contenere attributi
     * @param buffet, l'oggetto buffet appena creato
     * @param bindingResultBuffet, necessario per salvare eventuali errori
     * @param multipartFile, il file ottenuto attraverso l'input per inserire l'immagine dell'oggetto
     *                       buffet
     * @return se non ci sono errori, ci reindirizza al pannello di controllo. se ci sono errori invece
     * ci reindirizza allo stesso form avvisandoci degli errori presenti.
     * @throws IOException
     */
    @PostMapping("/new/buffet")
    private String addNewBuffet(Model model,
                                @Valid @ModelAttribute("buffet") Buffet buffet,
                                BindingResult bindingResultBuffet,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException {

        this.buffetValidator.validate(buffet, bindingResultBuffet);
        /*
            Normal use
         */
        if(!bindingResultBuffet.hasErrors()){
            String nomeFile = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            buffet.setPhoto(nomeFile);

            Buffet savedBuffet = this.buffetService.save(buffet);

            String uploadDir = "buffet-photo/" + savedBuffet.getId();

            FileUploadUtil.saveFile(uploadDir, nomeFile, multipartFile);
            return "redirect:/admin/controlpanel";
        }

        /*
        * FOR S3

        if(!bindingResultBuffet.hasErrors()){
            buffet.setPhoto(awss3Service.uploadFile(multipartFile));
            this.buffetService.save(buffet);
            return "redirect:/admin/controlpanel";
        }*/
        model.addAttribute("listChef", this.chefService.getAllChef());
        return "admin/buffetForm";
    }

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina contenente le informazioni del buffet
     * corrente.
     * @param model il modello della pagina, può contenere attributi
     * @param id l'identificatore (id) dell'oggetto buffet in questione
     * @param session la sessione corrente
     * @return buffet.html, la pagina di quel determinato buffet.
     */
    @GetMapping("/buffet/{id}")
    public String getBuffet(Model model,
                            @PathVariable("id") Long id, HttpSession session){
        Buffet buffet = this.buffetService.getBuffetById(id).get();

        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());

        model.addAttribute("credentials", this.credentialsService.getCredentialsAuthenticated());
        model.addAttribute("buffet", buffet);
        model.addAttribute("preferiti", this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti());
        return "buffet";
    }

    /**
     * Questo metodo gestisce l'eliminazione di un determinato buffet.
     * @param model il modello della pagina, può contenere attributi
     * @param id, l'identificatore (id) del buffet selezionato
     * @return se non ci sono errori, la pagina del pannello di controllo
     */
    @GetMapping("/admin/delete/buffet/{id}")
    public String deleteBuffet(Model model,
                               @PathVariable("id")Long id){
        Buffet buffet = this.buffetService.getBuffetById(id).get();

        if(this.buffetService.eliminaBuffetPerId(buffet)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }

    /**
     * Questo metodo gestisce l'aggiornamento del buffet.
     * @param model il modello della pagina, può contenere attributi
     * @param id, l'identificatore (id) del buffet selezionato
     * @return il form per aggiornare il buffet selezionato
     */
    @GetMapping("/admin/update/buffet/{id}")
    public String updateBuffet(Model model,
                               @PathVariable("id") Long id){
        Buffet buffet = this.buffetService.getBuffetById(id).get();
        model.addAttribute("buffet", buffet);
        model.addAttribute("listChef", this.chefService.getAllChef());
        model.addAttribute("listTipologia", this.tipologiaService.getAllTipologie());
        return "admin/buffetFormUpdate";
    }

    /**
     * Questo metodo gestisce l'invio dei dati al server una volta aggiornato il buffet selezionato.
     *
     * @param model il modello della pagina, può contenere attributi
     * @param buffet, l'oggetto buffet selezionato
     * @param bindingResultBuffet, necessario per eventuali errori
     * @param multipartFile, il file che rappresenta l'immagine del buffet
     * @return se non ci sono errori il pannello di controllo sennò il form per l'aggiornamento
     * del buffet con gli errori
     * @throws IOException
     */
    @PostMapping("/update/buffet")
    private String updateBuffet(Model model,
                                @Valid @ModelAttribute("buffet") Buffet buffet,
                                BindingResult bindingResultBuffet,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException {

        this.buffetValidator.validate(buffet, bindingResultBuffet);
        /*
            Normal use
         */
        if(!bindingResultBuffet.hasErrors()){
            String nomeFile = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            buffet.setPhoto(nomeFile);

            Buffet savedBuffet = this.buffetService.save(buffet);

            String uploadDir = "buffet-photo/" + savedBuffet.getId();

            FileUploadUtil.saveFile(uploadDir, nomeFile, multipartFile);
            return "redirect:/admin/controlpanel";
        }

        model.addAttribute("listChef", this.chefService.getAllChef());
        return "admin/buffetFormUpdate";
    }


}
