package br.com.nivlabs.gp.service.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.UserFilters;
import br.com.nivlabs.gp.models.dto.UserDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.userservice.business.CreateUserBusinessHandler;
import br.com.nivlabs.gp.service.userservice.business.SearchUserBusinessHandler;
import br.com.nivlabs.gp.service.userservice.business.UpdateUserBusinessHandler;
import br.com.nivlabs.gp.service.userservice.business.UpdateUserProfileBusinessHandler;

/**
 * 
 * Camada de serviços para manipulação de usuários do sistema
 *
 * @author viniciosarodrigues
 * @since 27-09-2021
 *
 */
@Service
public class UserService implements BaseService {

    @Autowired
    private SearchUserBusinessHandler searchUserBusinessHandler;
    @Autowired
    private UpdateUserBusinessHandler updateUserBusinessHandler;
    @Autowired
    private UpdateUserProfileBusinessHandler updateUserProfileBusinessHandler;
    @Autowired
    private CreateUserBusinessHandler createUserBusinessHandler;

    /**
     * Consulta página filtrada de usuários
     * 
     * @param filters Filtros de consulta
     * @param pageSettings Configurações de paginação
     * @return Página filtrada de usuários
     */
    public Page<UserDTO> searchEntityPage(UserFilters filters, Pageable pageSettings) {
        return searchUserBusinessHandler.getPage(filters, pageSettings);
    }

    /**
     * Busca um usuário por nome de usuário
     * 
     * @param username nome do usuário
     * @return Informações detalhadas do usuário
     */
    public UserInfoDTO findByUserName(String username) {
        return searchUserBusinessHandler.byUserName(username);
    }

    /**
     * Busca um usuário por nome de identificador único
     * 
     * @param id Identificador único do usuário
     * @return Informações detalhadas do usuário
     */
    public UserInfoDTO findById(Long id) {
        return searchUserBusinessHandler.byId(id);
    }

    /**
     * Consulta informações do usuário à partir do CPF
     * 
     * @param cpf CPF do usuário
     * @return Informações detalhadas do usuário
     */
    public UserInfoDTO findByCpf(String cpf) {
        return searchUserBusinessHandler.byCPF(cpf);
    }

    /**
     * Atualiza informações do usuário logado
     * 
     * @param request Informações à serem atualizadas
     * @return Informações atualizadas do usuário
     */
    public UserInfoDTO updateProfile(UserInfoDTO request) {
        return updateUserProfileBusinessHandler.update(request);
    }

    /**
     * Atualiza informações de um usuário
     * 
     * @param id Identificador único do usuário
     * @param request Informações do usuário
     * @return Informações do usuário atualizadas
     */
    public UserInfoDTO userUpdate(Long id, UserInfoDTO request) {
        request.setId(id);
        return updateUserBusinessHandler.update(request);
    }

    /**
     * Cria um usuário um usuário na aplicação
     * 
     * @param userInfo Informações do novo usuário
     * @return Informações do usuário criado
     */
    public UserInfoDTO create(UserInfoDTO userInfo) {
        return createUserBusinessHandler.create(userInfo);
    }
}
