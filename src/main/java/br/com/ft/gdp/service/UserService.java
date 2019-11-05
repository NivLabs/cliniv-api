package br.com.ft.gdp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.repository.UserRepository;

/**
 * Classe UserService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Service
public class UserService implements GenericService<UserApplication, Long> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepo;

    public UserApplication findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Username: " + username + ", tipo " + UserApplication.class.getName()));
    }

    @Override
    public Page<UserApplication> searchEntityPage(Pageable pageRequest) {
        return null;
    }

    @Override
    public UserApplication findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", tipo " + UserApplication.class.getName()));
    }

    @Override
    public UserApplication update(Long id, UserApplication entity) {
        return null;
    }

    @Override
    public UserApplication persist(UserApplication entity) {
        return userRepo.save(entity);
    }
}
