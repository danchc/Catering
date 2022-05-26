package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/controlpanel")
    public String getControlPanel(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        model.addAttribute("credentials", credentials);
        return "admin/controlpanel";
    }

}
