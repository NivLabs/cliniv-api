package br.com.nivlabs.gp.service.security;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.ForgotPasswordRequestDTO;
import br.com.nivlabs.gp.models.dto.NewPasswordRequestDTO;
import br.com.nivlabs.gp.repository.UserRepository;

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
    private static Logger logger;

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
                .findByUserName(newPasswordRequest.getUsername())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Usuário não encontrado para %s", newPasswordRequest.getUsername())));

        if (!usuario.getPerson().getBornDate().equals(newPasswordRequest.getBornDate())
                && !usuario.getPerson().getMotherName().equals(newPasswordRequest.getMotherName()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Você não forneceu as informações necessárias para recuperar a senha");

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
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "A nova senha e a confirmação de nova senha devem ser iguais");
        }
        UserApplication userFromDb = userRepo.findByUserName(userFromSession.getUsername())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (bc.matches(newPasswordDTO.getOldPassword(), userFromDb.getPassword())) {
            userFromDb.setPassword(bc.encode(newPasswordDTO.getNewPassword()));
        } else {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A senha atual não está correta");
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
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        logger.info("Paciente encontrado :: CPF -> {}", userFromDb.getPerson().getCpf());

        userFromDb.setPassword(bc.encode(userFromDb.getPerson().getCpf()));
        userRepo.saveAndFlush(userFromDb);
    }

}