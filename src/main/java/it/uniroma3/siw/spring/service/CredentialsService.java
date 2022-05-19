package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Transactional
    public Credentials save(Credentials credentials) {
        return this.credentialsRepository.save(credentials);
    }

    public boolean alreadyExists(String username) {
        return this.credentialsRepository.existsByUsername(username);
    }
}
