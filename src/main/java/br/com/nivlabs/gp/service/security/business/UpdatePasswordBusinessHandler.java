package br.com.nivlabs.gp.service.security.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.NewPasswordRequestDTO;
import br.com.nivlabs.gp.repository.UserRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.SecurityContextUtil;

/**
 * Componente de negócio para atualização de senha do usuário logado
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
@Component
public class UpdatePasswordBusinessHandler implements BaseBusinessHandler {

    @Autowired
    UserRepository userRepo;

    @Autowired
    BCryptPasswordEncoder bc;

    /**
     * Atualiza a senha do usuário logado
     * 
     * @param request
     * @param userFromContext
     */
    public void updatePasswordFromContextLogedUser(NewPasswordRequestDTO request) {
        var userFromContext = SecurityContextUtil.getAuthenticatedUser();
        if (userFromContext == null) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Nenhum usuário logado!");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "A nova senha e a confirmação de nova senha devem ser iguais");
        }
        UserApplication userFromDb = userRepo.findByUserName(userFromContext.getUsername())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (bc.matches(request.getOldPassword(), userFromDb.getPassword())) {
            userFromDb.setPassword(bc.encode(request.getNewPassword()));
        } else {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A senha atual não está correta");
        }
        userRepo.saveAndFlush(userFromDb);
    }
}
