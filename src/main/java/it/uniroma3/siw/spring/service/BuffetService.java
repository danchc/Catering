package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.repository.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BuffetService {

    @Autowired
    private BuffetRepository buffetRepository;

    @Transactional
    public Buffet save(Buffet buffet) {
        return this.buffetRepository.save(buffet);
    }

    @Transactional
    public List<Buffet> getAllBuffet() {
        return (List<Buffet>) this.buffetRepository.findAll();
    }

    public Optional<Buffet> getBuffetById(Long id) {
        return this.buffetRepository.findById(id);
    }
}
