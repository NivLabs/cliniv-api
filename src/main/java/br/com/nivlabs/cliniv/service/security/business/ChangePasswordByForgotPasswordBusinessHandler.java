package br.com.nivlabs.cliniv.service.security.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.ChangePasswordByForgotPasswordRequestDTO;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

@Component
public class ChangePasswordByForgotPasswordBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    UserRepository userRepo;

    @Autowired
    BCryptPasswordEncoder bc;

    /**
     * Cria uma nova senha para o processo de esquecimento de senha
     * 
     * @param request Requisição de nova senha por esquecimento
     */
    public void createNewPassword(ChangePasswordByForgotPasswordRequestDTO request) {
        logger.info("Iniciando processo de criação de nova senha");
        UserApplication usuario = userRepo
                .findByUserName(request.getUsername())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Usuário não encontrado para %s", request.getUsername())));

        if (!usuario.getPerson().getBornDate().equals(request.getBornDate())
                && !usuario.getPerson().getMotherName().equals(request.getMotherName()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Você não forneceu as informações necessárias para recuperar a senha");

        usuario.setPassword(bc.encode(request.getNewPassword()));
        userRepo.save(usuario);
    }

}
