package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public List<User> findAllUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    @Transactional
    public void add(User user, Buffet buffet){
        List<Buffet> preferiti = new ArrayList<>();
        preferiti.add(buffet);
        user.setPreferiti(preferiti);
        this.userRepository.save(user);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).get();
    }
}
