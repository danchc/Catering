package it.uniroma3.siw.spring.repository;

import it.uniroma3.siw.spring.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public boolean existsByEmail(String email);

    public Optional<User> findByEmail(String email);
}
