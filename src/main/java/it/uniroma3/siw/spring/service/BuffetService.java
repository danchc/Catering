package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.repository.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BuffetService {

    @Autowired
    private BuffetRepository buffetRepository;

    @Transactional
    public Buffet save(Buffet buffet) {
        return this.buffetRepository.save(buffet);
    }
}
