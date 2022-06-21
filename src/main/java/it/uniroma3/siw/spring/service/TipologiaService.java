package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Tipologia;
import it.uniroma3.siw.spring.repository.TipologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipologiaService {

    @Autowired
    protected TipologiaRepository tipologiaRepository;

    public List<Tipologia> getAllTipologie() {
        return (List<Tipologia>) this.tipologiaRepository.findAll();
    }
}
