package br.com.ft.gdp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.config.security.UserOfSystem;
import br.com.ft.gdp.exception.NewPasswordInvalidException;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.models.dto.ForgotPasswordRequestDTO;
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
    public void createNewPassword(ForgotPasswordRequestDTO newPasswordRequest) {
        UserApplication usuario = userRepo
                .findByUsernameOrEmail(newPasswordRequest.getUsernameOrEmail(), newPasswordRequest.getUsernameOrEmail())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Usuário não encontrado para o email/usuário: [%s]",
                                                                             newPasswordRequest.getUsernameOrEmail())));

        if (!usuario.getPerson().getBornDate().equals(newPasswordRequest.getBornDate())
                && !usuario.getPerson().getMotherName().equals(newPasswordRequest.getMotherName()))
            throw new NewPasswordInvalidException();

        usuario.setPassword(bc.encode(newPasswordRequest.getNewPassword()));
        userRepo.save(usuario);
    }

    /**
     * Altera a senha do usuário ativo na sessão
     * 
     * @param newPasswordDTO
     * @param userFromSession
     */
    public void updatePassword(NewPasswordRequestDTO newPasswordDTO, UserOfSystem userFromSession) {
        if (!newPasswordDTO.getNewPassword().equals(newPasswordDTO.getConfirmNewPassword())) {
            throw new ValidationException("A nova senha e a confirmação de nova senha devem ser iguais");
        }
        UserApplication userFromDb = userRepo.findByUsername(userFromSession.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        if (bc.matches(newPasswordDTO.getOldPassword(), userFromDb.getPassword())) {
            userFromDb.setPassword(bc.encode(newPasswordDTO.getNewPassword()));
        } else {
            throw new ValidationException("A senha atual não está correta");
        }
        userRepo.saveAndFlush(userFromDb);
    }

}