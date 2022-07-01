package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IngredienteValidator implements Validator {

    private final Integer MAX_LENGTH_DESCRIPTION = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingrediente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ingrediente ingrediente = (Ingrediente) target;

        String nome = ingrediente.getNome().trim();
        String desc = ingrediente.getDescrizione().trim();

        //non inserisco il nome
        if (nome.isEmpty()){
            errors.reject("ingrediente.nome.required");
        }
        //non inserisco la descrizione
        if (desc.isEmpty()){
            errors.reject("ingrediente.descrizione.required");
        } else if (desc.length() > MAX_LENGTH_DESCRIPTION){
            errors.reject("ingrediente.descrizione.size");
        }
    }
}
