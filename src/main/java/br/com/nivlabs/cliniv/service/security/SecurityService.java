package br.com.nivlabs.cliniv.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.ChangePasswordByForgotPasswordRequestDTO;
import br.com.nivlabs.cliniv.models.dto.CredentialsDTO;
import br.com.nivlabs.cliniv.models.dto.NewPasswordRequestDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.security.business.ChangePasswordByForgotPasswordBusinessHandler;
import br.com.nivlabs.cliniv.service.security.business.LoginBusinessHandler;
import br.com.nivlabs.cliniv.service.security.business.ResetPasswordBusinessHandler;
import br.com.nivlabs.cliniv.service.security.business.UpdatePasswordBusinessHandler;

/**
 * 
 * Fachada de serviços referêntes à segurança da aplicação
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
@Service
public class SecurityService implements BaseService {

    @Autowired
    ChangePasswordByForgotPasswordBusinessHandler changePasswordByForgotPasswordBusinessHandler;
    @Autowired
    UpdatePasswordBusinessHandler updatePasswordBusinessHandler;
    @Autowired
    ResetPasswordBusinessHandler resetPasswordBusinessHandler;
    @Autowired
    LoginBusinessHandler loginHandler;

    /**
     * Cria uma nova senha devido à esquecimento
     * 
     * @param request Requisição de troca de senha por esquecimento
     */
    public void createNewPasswordByForgotPassword(ChangePasswordByForgotPasswordRequestDTO request) {
        changePasswordByForgotPasswordBusinessHandler.createNewPassword(request);
    }

    /**
     * Altera a senha do usuário ativo na sessão
     * 
     * @param request Requisição de troca de senha
     * @param userFromSession Usuário logado na sessão
     */
    public void updatePassword(NewPasswordRequestDTO request) {
        updatePasswordBusinessHandler.updatePasswordFromContextLogedUser(request);
    }

    /**
     * Reseta a senha de determinado usuário
     * 
     * @param id Identificador único do usuário
     */
    public void resetPassword(Long id) {
        resetPasswordBusinessHandler.resetPassword(id);
    }

    /**
     * Realiza o login na aplicação
     * 
     * @param request Credenciais
     * @return Token
     */
    public String login(CredentialsDTO request, String customerId) {
        return loginHandler.login(request, customerId);
    }

}