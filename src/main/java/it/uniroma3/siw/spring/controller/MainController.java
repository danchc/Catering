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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    protected CredentialsService credentialsService;

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina principale del sito.
     * @param model
     * @param httpServletRequest
     * @return index.html
     */
    @GetMapping("/")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
        return "index";
    }


    /**
     * Questo metodo gestisce il reindirizzamento alla pagina dei contatti.
     * @param session
     * @return contacts.html
     */
    @GetMapping("/contacts")
    public String getContacts(HttpSession session) {
        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        return "contacts";
    }

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina 'Chi Siamo'
     * @param session
     * @return chisiamo.html
     */
    @GetMapping("/chisiamo")
    public String getChiSiamo() {
        return "chisiamo";
    }



}
