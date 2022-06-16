package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.model.Chef;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChefValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Chef.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Chef chef = (Chef) target;
        String nome = chef.getNome().trim();
        String cognome = chef.getCognome().trim();

        if(nome.isEmpty()) {
            errors.reject("chef.nome.required");
        }

        if(cognome.isEmpty()) {
            errors.reject("chef.cognome.required");
        }
    }
}
