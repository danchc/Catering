package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Transactional
    public Chef save(Chef chef){
        return this.chefRepository.save(chef);
    }
}
