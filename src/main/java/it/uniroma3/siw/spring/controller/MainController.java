package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.CustomOAuth2User;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    protected CredentialsService credentialsService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


    @GetMapping("/contacts")
    public String getContacts(HttpSession session) {
        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        return "contacts";
    }

    @GetMapping("/user")
    public String getUserProfile(Model model, Principal principal){
        principal.getName();
        List<Credentials> list = this.credentialsService.findAll();
        /*
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());*/
        model.addAttribute("credentials", this.credentialsService.getCredentials(principal.getName()));
        Credentials oauth = this.credentialsService.getCredentials(principal.getName());
        return "userprofile";
    }

}
