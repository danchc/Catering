package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    //injection
    @Autowired
    protected CredentialsValidator credentialsValidator;

    @Autowired
    protected CredentialsService credentialsService;

    /**
     * Il metodo gestisce il GET per andare nella pagina della registrazione.
     * @param model
     * @return register-form -> register-form.html
     */
    @GetMapping("/register-form")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "register-form";
    }

    @PostMapping("/register-form")
    public String register(Model model,
                           @ModelAttribute("user") User user,
                           BindingResult userBinding,
                           BindingResult credentialsBinding,
                           @ModelAttribute("credentials") Credentials credentials){

        this.credentialsValidator.validate(credentials, credentialsBinding);
        if(!credentialsBinding.hasErrors()){
            credentials.setUser(user);
            this.credentialsService.save(credentials);
            return "register-success";
        }
        return "register-form";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "login";
    }

}
