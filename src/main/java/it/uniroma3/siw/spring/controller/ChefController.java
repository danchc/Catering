package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.NazioneService;
import it.uniroma3.siw.upload.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Controller
public class ChefController {

    @Autowired
    protected ChefService chefService;

    @Autowired
    protected NazioneService nazioneService;

    @Autowired
    protected ChefValidator chefValidator;


    @GetMapping("/admin/new/chef")
    public String getChefForm(Model model){
        model.addAttribute("chef", new Chef());
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/chefForm";
    }

    @PostMapping("/new/chef")
    public String addNewChef(Model model,
                             @Valid @ModelAttribute("chef")Chef chef,
                             BindingResult bindingResultChef){
        this.chefValidator.validate(chef, bindingResultChef);
        //se tutto va bene puoi aggiungere i piatti
        if(!bindingResultChef.hasErrors()){
            this.chefService.save(chef);

            return "redirect:/admin/controlpanel";
        }
        model.addAttribute("nazioni", this.nazioneService.getAllNations());
        return "admin/chefForm";
    }

    @GetMapping("/admin/delete/chef/{id}")
    public String deleteChef(Model model,
                               @PathVariable("id")Long id){
        Chef chef = this.chefService.getChefById(id).get();

        if(this.chefService.eliminaChefPerId(chef)){
            return "redirect:/admin/controlpanel";
        }
        return "error";
    }

    @GetMapping("/admin/update/chef/{id}")
    public String updateChef(Model model,
                               @PathVariable("id") Long id){
        Chef chef = this.chefService.getChefById(id).get();
        model.addAttribute("chef", chef);
        model.addAttribute("nazioni", this.nazioneService.getAllNations());

        return "admin/chefFormUpdate";
    }

    @PostMapping("/update/chef")
    private String updateChef(Model model,
                               @Valid @ModelAttribute("chef") Chef chef,
                               BindingResult bindingResultChef) throws IOException {

        this.chefValidator.validate(chef, bindingResultChef);
        /*
            Normal use
         */
        if(!bindingResultChef.hasErrors()){
            this.chefService.save(chef);
            return "redirect:/admin/controlpanel";
        }

        return "admin/chefFormUpdate";
    }

}
