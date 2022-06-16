package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Transactional
    public User save(User user){
        return this.userRepository.save(user);
    }

    public boolean existsUserByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }
}
