package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.User;

/**
 * Validator for User
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    protected UserService userService;

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;
        String nome = user.getNome().trim();
        String cognome = user.getCognome().trim();
        String email = user.getEmail().trim();
        Long id = user.getId();


        //se non inserisco il nome
        if (nome.isEmpty()) {
            errors.reject("user.nome.required");
        }

        //se non inserisco il cognome
        if (cognome.isEmpty()) {
            errors.reject("user.cognome.required");
        }

        //se non inserisco l'email
        if (email.isEmpty()) {
            errors.reject("user.email.required");

        }
        //se la inserisco controllo se è già presente nel sistema
        else if (this.userService.existsUserByEmail(email) && user.getId() == null){
            errors.reject("user.email.duplicato");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

}

