package it.uniroma3.siw.spring.controller;

import com.nimbusds.jose.proc.SecurityContext;
import it.uniroma3.siw.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.spring.controller.validator.UserValidator;
import it.uniroma3.siw.spring.model.*;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.UserService;
import it.uniroma3.siw.upload.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected BuffetService buffetService;

    @Autowired
    protected UserValidator userValidator;

    @Autowired
    protected CredentialsValidator credentialsValidator;


    /**
     * Questo metodo gestisce l'invio al server per quanto riguarda l'aggiunta di un buffet ai
     * preferiti dell'utente autenticato.
     * @param id
     * @param model
     * @return buffets.html
     */
    @GetMapping("/newPreferito/{id}")
    public String addPreferito(@PathVariable("id")Long id,
                               Model model) {
        Buffet buffet = this.buffetService.getBuffetById(id).get();
        List<Buffet> preferiti = this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti();
        if(preferiti.size() == 0) {
            preferiti.add(buffet);
        } else if(preferiti.contains(buffet)) {
                    logger.info("#### preferito già esistente ####");
                    return "redirect:/buffet/{id}";
        } else {
            preferiti.add(buffet);
        }

        this.credentialsService.update(this.credentialsService.getCredentialsAuthenticated());
        return "redirect:/buffets";
    }

    /**
     * Questo metodo gestisce l'eliminazione di un preferito dalla lista dei preferiti di un utente loggato.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/deletePreferito/{id}")
    public String deletePreferito(@PathVariable("id")Long id,
                               Model model) {
        logger.info("#### deleting buffet from preferiti ... ####");
        Buffet buffet = this.buffetService.getBuffetById(id).get();
        List<Buffet> preferiti = this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti();
        Iterator<Buffet> it = preferiti.iterator();
        while(it.hasNext()){
            Buffet b = it.next();
            if(b.getId().equals(buffet.getId())){
                it.remove();
                System.out.println("size: " + preferiti.size());
            }
        }
        this.credentialsService.update(this.credentialsService.getCredentialsAuthenticated());
        logger.info("#### deleted buffet from preferiti .###");
        return "redirect:/user";
    }

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina del profilo dell'utente.
     * @param model
     * @return userprofile.html
     */
    @GetMapping("/user")
    public String getUserProfile(Model model){
        model.addAttribute("credentials", this.credentialsService.getCredentialsAuthenticated());
        model.addAttribute("preferiti", this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti());
        return "userprofile";
    }

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina per modificare i dati di un determinato
     * utente autenticato.
     * @param id
     * @param model
     * @return userprofileupdate.html
     */
    @GetMapping("/update/info/user/{id}")
    public String getUpdateForm(@PathVariable("id") Long id, Model model){
        Credentials credentials = this.credentialsService.findById(id);
        model.addAttribute("credentials", credentials);
        model.addAttribute("user", credentials.getUser());
        return "userprofileUpdate";
    }

    /**
     * Questo metodo gestisce l'invio dei dati al server per aggiornare le informazioni
     * dell'utente che ha richiesto di aggiornare i suoi dati personali.
     *
     * @param credentials
     * @param user
     * @param bindingResult
     * @return user.html se non ci sono errori, userprofileupdate.html altrimenti
     */
    @PostMapping("/update/info/user")
    public String updateUserInfo(@Valid @ModelAttribute("credentials") Credentials credentials,
                                 @Valid @ModelAttribute("user") User user,
                                 BindingResult bindingResult) {

        this.userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "userprofileUpdate";
        }

        credentials.setUser(user);
        credentials.setProvider(Provider.LOCAL);
        credentials.setRuolo(Credentials.RUOLO_DEFAULT);

        this.credentialsService.update(credentials);

        return "redirect:/user";
    }
}
