package br.com.ft.gdp.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

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
                .findByUserNameOrEmail(newPasswordRequest.getUsernameOrEmail(), newPasswordRequest.getUsernameOrEmail())
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
        UserApplication userFromDb = userRepo.findByUserName(userFromSession.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        if (bc.matches(newPasswordDTO.getOldPassword(), userFromDb.getPassword())) {
            userFromDb.setPassword(bc.encode(newPasswordDTO.getNewPassword()));
        } else {
            throw new ValidationException("A senha atual não está correta");
        }
        userRepo.saveAndFlush(userFromDb);
    }

    /**
     * Reseta a senha do usuário
     * 
     * @param id
     */
    public void resetPassword(Long id) {
        logger.info("Iniciando busca de paciente para reset de senha :: ID -> {}", id);
        UserApplication userFromDb = userRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        logger.info("Paciente encontrado :: CPF -> {}", userFromDb.getPerson().getCpf());

        userFromDb.setPassword(bc.encode(userFromDb.getPerson().getCpf()));
        userRepo.saveAndFlush(userFromDb);
    }

}