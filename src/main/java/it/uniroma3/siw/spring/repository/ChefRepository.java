package it.uniroma3.siw.spring.repository;

import it.uniroma3.siw.spring.model.Chef;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChefRepository extends CrudRepository<Chef, Long> {

    public Chef existsByNomeAndCognomeAndNazio(String nome);
}
