package br.com.nivlabs.gp.service.userservice.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.util.SecurityContextUtil;

/**
 * Componente específico para atualização cadastral do usuário logado
 * 
 *
 * @author viniciosarodrigues
 * @since 28-09-2021
 *
 */
@Component
public class UpdateUserProfileBusinessHandler extends UpdateUserBusinessHandler {

    /**
     * Atualiza ou cria informações de usuários
     * 
     * @param userId
     * @param entity
     * @return
     */
    @Override
    public UserInfoDTO update(UserInfoDTO entity) {
        validUserToUpdate(entity);
        super.update(entity);
        return entity;
    }

    /**
     * 
     * Verifica se o usuário que está sendo alterado é o mesmo da sessão
     * 
     * @param entity
     * @param userFromSession
     */
    private void validUserToUpdate(UserInfoDTO entity) {
        UserApplication userEntity = userRepo.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername())
                .orElseThrow(() -> new HttpException(HttpStatus.FORBIDDEN,
                        "Operação não permitida, você só pode editar o seu próprio perfil"));

        if (!userEntity.getUserName().equals(entity.getUserName()))
            throw new HttpException(HttpStatus.FORBIDDEN,
                    "Operação não permitida, você só pode editar o seu próprio perfil");
        entity.setId(userEntity.getId());
    }
}
