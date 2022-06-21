package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected BuffetService buffetService;

    @PostMapping("/newPreferito")
    public String addPreferito(@ModelAttribute("id") Long id, Model model) {

        User loggedUser = this.credentialsService.getCredentialsAuthenticated().getUser();
        logger.debug("preferiti #####");
        loggedUser.addPreferito(this.buffetService.getBuffetById(id).get());
        logger.debug("preferiti aggiornati #####");
        return "redirect:/buffet/"+id;
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
        model.addAttribute("preferiti", this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti());
        return "userprofile";
    }
}
