package it.uniroma3.siw.spring.controller;

import com.nimbusds.jose.proc.SecurityContext;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/newPreferito/{id}")
    public String addPreferito(@PathVariable(value="id") Long id,
                               Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        credentials.getUser().addPreferito(this.buffetService.getBuffetById(id).get());
        return "redirect:/buffet/"+id;
    }

    @GetMapping("/user")
    public String getUserProfile(Model model){
        model.addAttribute("credentials", this.credentialsService.getCredentialsAuthenticated());
        model.addAttribute("preferiti", this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti());
        return "userprofile";
    }
}
