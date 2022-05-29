package it.uniroma3.siw.spring.controller;


import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    @GetMapping("/buffetForm")
    public String getBuffetForm(Model model) {
        model.addAttribute("buffet", new Buffet());
        model.addAttribute("chef", new Chef());
        return "admin/buffetForm";
    }

    @PostMapping("/addBuffet")
    private String addNewBuffet(Model model,
                                @ModelAttribute("buffet") Buffet buffet,
                                @ModelAttribute("chef") Chef chef,
                                BindingResult bindingResultBuffet){
        if(!bindingResultBuffet.hasErrors()){
            buffet.setChef(chef);
            this.buffetService.save(buffet);
            model.addAttribute("buffet", buffet);
            return "admin/piattoForm";
        }
           return "admin/buffetForm";

    }

}
