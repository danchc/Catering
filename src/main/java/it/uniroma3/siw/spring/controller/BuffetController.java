package it.uniroma3.siw.spring.controller;


import it.uniroma3.siw.spring.controller.validator.BuffetValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.AWSS3Service;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
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
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;


@Controller
public class BuffetController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected BuffetService buffetService;

    @Autowired
    protected ChefService chefService;

    @Autowired
    protected BuffetValidator buffetValidator;

    @Autowired
    protected AWSS3Service awss3Service;

    @GetMapping("/buffets")
    public String getBuffets(HttpSession session, Model model) {
        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        model.addAttribute("listBuffet", this.buffetService.getAllBuffet());
        return "buffets";
    }

    @GetMapping("/admin/new/buffet")
    public String getBuffetForm(Model model) {
        model.addAttribute("buffet", new Buffet());
        model.addAttribute("listChef", this.chefService.getAllChef());
        return "admin/buffetForm";
    }

    @PostMapping("/new/buffet")
    private String addNewBuffet(Model model,
                                @Valid @ModelAttribute("buffet") Buffet buffet,
                                BindingResult bindingResultBuffet,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException {

        this.buffetValidator.validate(buffet, bindingResultBuffet);
        /*
            Normal use
         */
        if(!bindingResultBuffet.hasErrors()){
            String nomeFile = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            buffet.setPhoto(nomeFile);

            Buffet savedBuffet = this.buffetService.save(buffet);

            String uploadDir = "buffet-photo/" + savedBuffet.getId();

            FileUploadUtil.saveFile(uploadDir, nomeFile, multipartFile);
            return "redirect:/admin/controlpanel";
        }

        /*
        * FOR S3

        if(!bindingResultBuffet.hasErrors()){
            buffet.setPhoto(awss3Service.uploadFile(multipartFile));
            this.buffetService.save(buffet);
            return "redirect:/admin/controlpanel";
        }*/
        model.addAttribute("listChef", this.chefService.getAllChef());
        return "admin/buffetForm";
    }

    @GetMapping("/buffet/{id}")
    public String getBuffet(Model model,
                            @PathVariable("id") Long id, HttpSession session){
        Buffet buffet = this.buffetService.getBuffetById(id).get();

        session.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());

        model.addAttribute("buffet", buffet);
        return "buffet";
    }

    @GetMapping("/admin/delete/buffet/{id}")
    public String deleteBuffet(Model model,
                               @PathVariable("id")Long id){
        Buffet buffet = this.buffetService.getBuffetById(id).get();

        if(this.buffetService.eliminaBuffetPerId(buffet)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }
}
