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

    /**
     * Questo metodo gestisce il salvataggio nel database di un oggetto di tipo buffet.
     * @param buffet
     * @return
     */
    @Transactional
    public Buffet save(Buffet buffet) {
        return this.buffetRepository.save(buffet);
    }

    /**
     * Con questo metodo recuperiamo tutti i buffet all'interno del database.
     * @return
     */
    @Transactional
    public List<Buffet> getAllBuffet() {
        return (List<Buffet>) this.buffetRepository.findAll();
    }

    /**
     * Con questo metodo recuperiamo il buffet in base all'id.
     * @param id
     * @return
     */
    public Optional<Buffet> getBuffetById(Long id) {
        return this.buffetRepository.findById(id);
    }

    /**
     * Il metodo gestisce l'eliminazione di un buffet in base ad un id.
     * @param buffet
     * @return
     */
    @Transactional
    public boolean eliminaBuffetPerId(Buffet buffet) {
        try{
            this.buffetRepository.delete(buffet);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
