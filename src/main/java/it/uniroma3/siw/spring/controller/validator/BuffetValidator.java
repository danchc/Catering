package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.service.BuffetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BuffetValidator implements Validator {

    private final Integer MAX_LENGTH_DESCRIPTION = 255;


    @Override
    public boolean supports(Class<?> clazz) {
        return Buffet.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Buffet buffet = (Buffet) target;

        //variabili del buffet inserito
        String nome = buffet.getNome().trim();
        String desc = buffet.getDescrizione().trim();

        //non inserisco il nome
        if (nome.isEmpty()){
            errors.reject("buffet.nome.required");
        }
        //non inserisco la descrizione
        if (desc.isEmpty()){
            errors.reject("buffet.descrizione.required");
        } else if (desc.length() > MAX_LENGTH_DESCRIPTION){
            errors.reject("buffet.descrizione.size");
        }


    }
}
