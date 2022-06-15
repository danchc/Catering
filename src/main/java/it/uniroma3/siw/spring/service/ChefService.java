package it.uniroma3.siw.spring.service;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Transactional
    public Chef save(Chef chef){
        return this.chefRepository.save(chef);
    }

    public List<Chef> getAllChef(){
        return (List<Chef>) this.chefRepository.findAll();
    }

    public Optional<Chef> getChefById(Long id) {
        return this.chefRepository.findById(id);
    }

    @Transactional
    public boolean eliminaChefPerId(Chef chef) {
        try{
            this.chefRepository.delete(chef);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
