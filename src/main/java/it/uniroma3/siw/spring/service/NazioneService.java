package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Nazione;
import it.uniroma3.siw.spring.repository.NazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NazioneService {

    @Autowired
    protected NazioneRepository nazioneRepository;

    /**
     * Questo metodo recupera tutte le nazioni all'interno del database.
     * @return
     */
    public List<Nazione> getAllNations(){
        return (List<Nazione>) this.nazioneRepository.findAll();
    }
}
