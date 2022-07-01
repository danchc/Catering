package it.uniroma3.siw.spring.controller;

import com.nimbusds.jose.proc.SecurityContext;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.UserService;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected BuffetService buffetService;

    @PostMapping("/newPreferito")
    public String addPreferito(@RequestParam(required=false, name="nome") String nome,
                               Model model) {


        return "redirect:/buffets";
    }

    @GetMapping("/user")
    public String getUserProfile(Model model){
        model.addAttribute("credentials", this.credentialsService.getCredentialsAuthenticated());
        model.addAttribute("preferiti", this.credentialsService.getCredentialsAuthenticated().getUser().getPreferiti());
        return "userprofile";
    }
}
