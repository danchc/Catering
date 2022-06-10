package it.uniroma3.siw.spring.controller;


import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class BuffetController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    private BuffetService buffetService;

    @GetMapping("/buffets")
    public String getBuffets(HttpSession session) {
        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        return "buffets";
    }

    @GetMapping("/buffetForm")
    public String getBuffetForm(Model model) {
        model.addAttribute("buffet", new Buffet());
        return "admin/buffetForm";
    }

    @PostMapping("/addBuffet")
    private String addNewBuffet(Model model,
                                @ModelAttribute("buffet") Buffet buffet,
                                BindingResult bindingResultBuffet,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(!bindingResultBuffet.hasErrors()){
            String nomeFile = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            buffet.setPhoto(nomeFile);

            Buffet savedBuffet = this.buffetService.save(buffet);

            String uploadDir = "buffet-photo/" + savedBuffet.getId();

            FileUploadUtil.saveFile(uploadDir, nomeFile, multipartFile);
            model.addAttribute("buffet", buffet);
            return "redirect:/controlpanel";
        }
           return "admin/buffetForm";
    }


}
