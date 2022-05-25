package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialsRepository credentialsRepo;

    @Transactional
    public Credentials save(Credentials cred) {
        cred.setRuolo(Credentials.RUOLO_DEFAULT);
        cred.setPassword(this.passwordEncoder.encode(cred.getPassword()));
        return this.credentialsRepo.save(cred);
    }

    public List<Credentials> findAll() {
        List<Credentials> credenziali = new ArrayList<>();
        for(Credentials c : this.credentialsRepo.findAll()){
            credenziali.add(c);
        }
        return credenziali;
    }

    public Credentials getCredentials(String username) {
        return this.credentialsRepo.findByUsername(username).get();
    }

    public boolean alreadyExistsUsername(String username){
        return credentialsRepo.existsByUsername(username);
    }

}
