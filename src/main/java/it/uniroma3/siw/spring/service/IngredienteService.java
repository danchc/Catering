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

    @Transactional
    public Ingrediente save(Ingrediente ingrediente){
        return this.ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> getAllIngredients() {
        return (List<Ingrediente>) this.ingredienteRepository.findAll();
    }

    public Optional<Ingrediente> getIngredientePerId(Long id) {
        return this.ingredienteRepository.findById(id);
    }

    public boolean eliminaIngredientePerId(Ingrediente ingrediente){
        try {
            this.ingredienteRepository.delete(ingrediente);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
