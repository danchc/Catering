package it.uniroma3.siw.spring.repository;

import it.uniroma3.siw.spring.model.Chef;
import org.springframework.data.repository.CrudRepository;

public interface ChefRepository extends CrudRepository<Chef, Long> {
}
