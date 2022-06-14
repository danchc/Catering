package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IngredienteService {

    @Autowired
    protected IngredienteService ingredienteService;

    @Transactional
    public Ingrediente save(Ingrediente ingrediente){
        return this.ingredienteService.save(ingrediente);
    }
}
