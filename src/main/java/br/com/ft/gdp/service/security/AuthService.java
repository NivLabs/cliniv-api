package br.com.ft.gdp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.NewPasswordInvalidException;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.models.dto.NewPasswordRequestDTO;
import br.com.ft.gdp.repository.UserRepository;

/**
 * Classe AuthService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Service
public class AuthService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    BCryptPasswordEncoder bc;

    /**
     * Cria nova senha
     * 
     * @param newPasswordRequest
     */
    public void createNePassword(NewPasswordRequestDTO newPasswordRequest) {
        UserApplication usuario = userRepo
                .findByUsernameOrEmail(newPasswordRequest.getUsernameOrEmail(), newPasswordRequest.getUsernameOrEmail())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Usuário não encontrado para o email/usuário: [%s]",
                                                                             newPasswordRequest.getUsernameOrEmail())));

        if (!usuario.getBornDate().equals(newPasswordRequest.getBornDate())
                && !usuario.getMotherName().equals(newPasswordRequest.getMotherName()))
            throw new NewPasswordInvalidException();

        usuario.setPassword(bc.encode(newPasswordRequest.getNewPassword()));
        userRepo.save(usuario);
    }

}