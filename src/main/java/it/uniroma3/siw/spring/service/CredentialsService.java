package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.CustomOAuth2User;
import it.uniroma3.siw.spring.model.Provider;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class CredentialsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialsRepository credentialsRepo;

    /**
     * Questo metodo gestisce il salvataggio nel database di un oggetto di tipo credentials.
     * Inoltre si imposta il ruolo e si codifica la password.
     * @param credentials
     * @return
     */
    @Transactional
    public Credentials save(Credentials credentials) {
        credentials.setRuolo(Credentials.RUOLO_DEFAULT);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepo.save(credentials);
    }

    /**
     * Il metodo gestisce l'update delle informazioni di un utente.
     * @param credentials
     * @return
     */
    @Transactional
    public Credentials update(Credentials credentials) {
        return this.credentialsRepo.save(credentials);
    }

    /**
     * Con questo metodo andiamo a cercare tutte le credenziali salvate nel database.
     * @return
     */
    public List<Credentials> findAll() {
        List<Credentials> credenziali = new ArrayList<>();
        for(Credentials c : this.credentialsRepo.findAll()){
            credenziali.add(c);
        }
        return credenziali;
    }

    /**
     * Con questo metodo recuperiamo le credenziali in base all'username.
     * @param username
     * @return
     */
    public Credentials getCredentials(String username) {
        return this.credentialsRepo.findByUsername(username).get();
    }

    /**
     * Questo metodo ci dice se esiste o meno un utente con quell'username all'interno
     * del database.
     * @param username
     * @return
     */
    public boolean alreadyExistsUsername(String username){
        return credentialsRepo.existsByUsername(username);
    }

    /**
     * Questo metodo gestisce il recupero delle informazioni dell'utente loggato.
     * @return
     */
    public Credentials getCredentialsAuthenticated(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.getCredentials(userDetails.getUsername());
        return credentials;
    }

    /**
     * Questo metodo gestisce le operazioni dopo l'accesso di un utente attraverso
     * l'utilizzo di OAuth2.
     * @param username
     * @param oAuth2User
     */
    public void processOAuthPostLogin(String username, CustomOAuth2User oAuth2User) {

        if (!this.credentialsRepo.existsByUsername(username)) {
            Credentials newCredentials = new Credentials();
            User newUser = new User();
            newUser.setNome(oAuth2User.getAttribute("given_name"));
            newUser.setCognome(oAuth2User.getAttribute("family_name"));
            newUser.setEmail(oAuth2User.getAttribute("email"));
            newCredentials.setUser(newUser);
            newCredentials.setUsername(oAuth2User.getUsername());
            newCredentials.setPassword(UUID.randomUUID().toString());
            newCredentials.setProvider(Provider.GOOGLE);
            newCredentials.setRuolo(Credentials.RUOLO_DEFAULT);

            credentialsRepo.save(newCredentials);
        }

    }

    /**
     * Con questo metodo recuperiamo un utente in base all'id.
     * @param id
     * @return
     */
    public Credentials findById(Long id) {
        return this.credentialsRepo.findById(id).get();
    }
}
