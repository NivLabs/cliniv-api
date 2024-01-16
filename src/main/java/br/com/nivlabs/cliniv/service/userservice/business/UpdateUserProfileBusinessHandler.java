package br.com.nivlabs.cliniv.service.userservice.business;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.UserInfoDTO;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

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
    @Transactional
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
    @Transactional
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
