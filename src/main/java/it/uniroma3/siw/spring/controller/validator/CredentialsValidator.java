package it.uniroma3.siw.spring.controller.validator;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {

    public final static Integer MIN_USERNAME_LENGTH = 5;
    public final static Integer MAX_USERNAME_LENGTH = 15;
    public final static Integer MIN_PASSWORD_LENGTH = 8;

    @Autowired
    private CredentialsService credentialsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Credentials credentials = (Credentials) target;
        String username = credentials.getUsername().trim();
        String password = credentials.getPassword().trim();

        /* controllo username */
        if(username.isEmpty()){
            errors.reject("required.credentials.username");
        } else if(username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH){
            errors.reject("size.credentials.username");
        } else if(this.credentialsService.alreadyExistsUsername(username)){
            errors.reject("duplicato.credentials.username");
        }

        /* controllo password */
        if(password.isEmpty()){
            errors.reject("required.credentials.password");
        } else if(password.length() < MIN_PASSWORD_LENGTH){
            errors.reject("size.credentials.password");
        }
    }
}
