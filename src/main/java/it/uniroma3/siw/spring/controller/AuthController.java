package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Credentials;
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

@Controller
public class AuthController {

    //injection


    @Autowired
    protected CredentialsService credentialsService;

    /**
     * Il metodo gestisce il GET per andare nella pagina della registrazione.
     * @param model
     * @return register -> register.html
     */
    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("cred", new Credentials());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @ModelAttribute("user") User user,
                           BindingResult userBinding,
                           BindingResult credentialsBinding,
                           @ModelAttribute("credentials") Credentials credentials){

       // this.credentialsValidator.validate(credentials, credentialsBinding);
        if(!credentialsBinding.hasErrors()){
            credentials.setUser(user);
            this.credentialsService.save(credentials);
            return "register-success";
        }
        return "register";
    }


    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/default")
    public String getDefault(Model model,
                              @ModelAttribute("user") User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN)) {
            model.addAttribute("user", user);
            return "/admin/home";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
            return "home";
        }

    }

    @GetMapping("/logout")
    public String getLoggedOut(Model model) {
        return "index";
    }

}
