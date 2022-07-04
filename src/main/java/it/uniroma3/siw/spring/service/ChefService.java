package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    /**
     * Con questo metodo salviamo uno chef nel database.
     * @param chef entità da salvare
     * @return se ha successo, lo chef salvato
     */
    @Transactional
    public Chef save(Chef chef){
        return this.chefRepository.save(chef);
    }

    /**
     * Con questo metodo otteniamo la lista di tutti gli chef salvati nel database.
     * @return la lista
     */
    public List<Chef> getAllChef(){
        return (List<Chef>) this.chefRepository.findAll();
    }

    /**
     * Con questo metodo si cerca lo chef in base ad un determinato id.
     * @param id
     * @return lo chef trovato per id
     */
    public Optional<Chef> getChefById(Long id) {
        return this.chefRepository.findById(id);
    }


    /**
     * Questo metodo gestisce l'eliminazione di un certo chef in base all'id passato come parametro.
     * @param chef
     * @return true o false
     */
    @Transactional
    public boolean eliminaChefPerId(Chef chef) {
        try{
            this.chefRepository.delete(chef);
            return true;
        }catch (Exception e) {
            return false;
        }
    }


}
