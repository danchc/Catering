package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class IngredienteService {

    @Autowired
    protected IngredienteRepository ingredienteRepository;

    /**
     * Questo metodo gestisce il salvataggio nel database di un oggetto di tipo ingrediente.
     * @param ingrediente
     * @return
     */
    @Transactional
    public Ingrediente save(Ingrediente ingrediente){
        return this.ingredienteRepository.save(ingrediente);
    }

    /**
     * Questo metodo recupera tutti gli ingredienti salvati nel database.
     * @return
     */
    public List<Ingrediente> getAllIngredients() {
        return (List<Ingrediente>) this.ingredienteRepository.findAll();
    }

    /**
     * Questo metodo recupera un determinato ingrediente in base all'id.
     * @param id
     * @return
     */
    public Optional<Ingrediente> getIngredientePerId(Long id) {
        return this.ingredienteRepository.findById(id);
    }

    /**
     * Questo metodo gestisce l'eliminazione di un ingrediente in base all'id.
     * @param ingrediente
     * @return
     */
    @Transactional
    public boolean eliminaIngredientePerId(Ingrediente ingrediente){
        try {
            this.ingredienteRepository.delete(ingrediente);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
