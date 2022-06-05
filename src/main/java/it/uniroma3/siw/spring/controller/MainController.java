package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/buffets")
    public String getBuffets(Model model) {
        return "buffets";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        model.addAttribute("chef", new Chef());
        return "contacts";
    }

    @GetMapping("/user")
    public String getUserProfile(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        model.addAttribute(credentials);
        return "userprofile";
    }
}
