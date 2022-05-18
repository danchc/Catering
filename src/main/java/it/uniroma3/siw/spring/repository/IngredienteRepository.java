package it.uniroma3.siw.spring.repository;

import it.uniroma3.siw.spring.model.Ingrediente;
import org.springframework.data.repository.CrudRepository;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
}
