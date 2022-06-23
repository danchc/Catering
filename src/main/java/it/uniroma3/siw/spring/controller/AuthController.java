package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.spring.controller.validator.UserValidator;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Provider;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class AuthController {

    //injection
    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected UserValidator userValidator;

    @Autowired
    protected CredentialsValidator credentialsValidator;

    /**
     * Il metodo gestisce il GET per andare nella pagina della registrazione.
     * @param model, model
     * @return register -> register.html
     */
    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Metodo per salvare il nuovo utente ed inviare dati al server.
     *
     * @param user oggetto utente
     * @param credentials oggetto credenziali
     * @param bindingResult necessario per inglobare errori
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           @Valid @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResult){


        /* verifiche */
        this.userValidator.validate(user, bindingResult);
        this.credentialsValidator.validate(credentials, bindingResult);
        if(bindingResult.hasErrors()){
            return "register";
        }
        credentials.setUser(user);
        credentials.setProvider(Provider.LOCAL);
        credentials.setPhoto(null);
        this.credentialsService.save(credentials);
        return "register-success";
    }


    /**
     * Metodo per ottenere la pagina del login.
     * @return la pagina del login -> login.html
     */
    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    /**
     * Questo metodo è necessario per reindirizzare l'utente appena loggato alla pagina di default.
     *
     * @param model il modello della pagina corrente, può contenere attributi
     * @param user l'utente appena loggato
     * @param session la sessione corrente
     * @return
     */
    @GetMapping("/default")
    public String getDefault(Model model,
                             @ModelAttribute("user") User user,
                             HttpSession session) {
       Credentials credentials = this.credentialsService.getCredentialsAuthenticated();
       session.setAttribute("provider", credentials.getProvider());

        if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN)) {
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
            return "admin/home";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
            return "home";
        }

    }

    /**
     * Questo metodo gestisce il logout dell'utente loggato.
     * @return index.html
     */
    @GetMapping("/logout")
    public String getLoggedOut() {
        return "index";
    }

}
