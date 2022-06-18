package it.uniroma3.siw.spring.repository;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    public Optional<Credentials> findByUsername(String username);

    public boolean existsByUsername(String username);

    public Optional<Credentials> findByProviderAndUsername(Provider provider, String username);


}
