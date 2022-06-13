package it.uniroma3.siw.spring.repository;

import it.uniroma3.siw.spring.model.Piatto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

}
