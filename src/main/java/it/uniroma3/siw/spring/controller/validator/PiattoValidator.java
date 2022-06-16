package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.model.Piatto;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class PiattoValidator implements Validator {

    private final Integer MAX_LENGTH_DESCRIPTION = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return Piatto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Piatto piatto = (Piatto) target;
        String nome = piatto.getNome().trim();
        String desc = piatto.getDescrizione().trim();

        //non inserisco il nome
        if (nome.isEmpty()){
            errors.reject("piatto.nome.required");
        }
        //non inserisco la descrizione
        if (desc.isEmpty()){
            errors.reject("piatto.descrizione.required");
        } else if (desc.length() > MAX_LENGTH_DESCRIPTION){
            errors.reject("piatto.descrizione.size");
        }
    }
}
