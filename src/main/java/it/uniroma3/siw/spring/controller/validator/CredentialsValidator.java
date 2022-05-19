package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {

    /* rules */
    protected final static Integer MAX_USERNAME_LENGTH = 15;
    protected final static Integer MIN_USERNAME_LENGTH = 3;
    protected final static Integer MIN_PASSWORD_LENGTH = 6;

    /*injection*/
    @Autowired
    protected CredentialsService credentialsService;

    @Override
    public void validate(Object target, Errors errors) {
        Credentials credentials = (Credentials) target;

        //controllo duplicazione
        if(this.credentialsService.alreadyExists(credentials.getUsername())){
            errors.reject("credentials.duplicato");
        }

        //controllo regole
        if(credentials.getUsername().length() < MIN_USERNAME_LENGTH || credentials.getUsername().length() > MAX_USERNAME_LENGTH){
            errors.reject("credentials.username.size");
        }

        if(credentials.getPassword().length() < MIN_USERNAME_LENGTH){
            errors.reject("credentials.password.size");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }
}
