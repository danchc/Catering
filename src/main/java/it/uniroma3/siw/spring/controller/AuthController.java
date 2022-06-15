package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.spring.controller.validator.UserValidator;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.CustomOAuth2User;
import it.uniroma3.siw.spring.model.Provider;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;

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
     * @param model
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
                           BindingResult userBinding,
                           BindingResult credentialsBinding,
                           @Valid @ModelAttribute("credentials") Credentials credentials){
        this.userValidator.validate(user, userBinding);
        this.credentialsValidator.validate(credentials, credentialsBinding);
        if(!credentialsBinding.hasErrors() && !userBinding.hasErrors()){
            credentials.setUser(user);
            credentials.setProvider(Provider.LOCAL);
            credentials.setPhoto(null);
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
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

        if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN)) {
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
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
