package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.PiattoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PiattoService {


    @Autowired
    protected PiattoRepository piattoRepository;

    @Transactional
    public Piatto save(Piatto piatto) {
        return this.piattoRepository.save(piatto);
    }

    @Transactional
    public List<Piatto> getAllPiatti() {
        return (List<Piatto>) this.piattoRepository.findAll();
    }
}
