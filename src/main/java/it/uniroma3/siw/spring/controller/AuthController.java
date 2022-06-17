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
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @Valid @ModelAttribute("user") User user,
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


    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/default")
    public String getDefault(Model model,
                              @ModelAttribute("user") User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

        if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN)) {
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
            return "/admin/home.html";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
            return "home";
        }

    }

    @GetMapping("/logout")
    public String getLoggedOut() {
        return "index";
    }

}
