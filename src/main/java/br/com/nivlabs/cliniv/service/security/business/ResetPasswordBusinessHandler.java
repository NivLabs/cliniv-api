package br.com.nivlabs.cliniv.service.security.business;

import br.com.nivlabs.cliniv.config.security.UserOfSystem;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente de negócio para Reset de senha do usuário
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 */
@Component
public class ResetPasswordBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder bc;

    /**
     * Reseta a senha de um usuário da aplicação
     *
     * @param id Identificador único do usuário que terá a senha resetada
     */
    @Transactional
    public void resetPassword(Long id) {
        UserOfSystem userFromContext = SecurityContextUtil.getAuthenticatedUser();
        if (userFromContext == null) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Nenhum usuário logado, operação não permitida!");
        }
        logger.info("Reset de senha solicitado pelo usuário ::  Usuário: {} | Nome: {}", userFromContext.getUsername(),
                userFromContext.getPersonName());
        logger.info("Iniciando busca de paciente para reset de senha :: ID -> {}", id);
        UserApplication userFromDb = userRepo.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        logger.info("Paciente encontrado :: CPF -> {}", userFromDb.getPerson().getCpf());

        userFromDb.setPassword(bc.encode(userFromDb.getPerson().getCpf()));

        userRepo.saveAndFlush(userFromDb);
    }
}
