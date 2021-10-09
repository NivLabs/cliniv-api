package br.com.nivlabs.gp.service.procedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ProcedureFilters;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.dto.ProcedureInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.procedure.business.CreateProcedureBusinessHandler;
import br.com.nivlabs.gp.service.procedure.business.DeleteProcedureBusinessHandler;
import br.com.nivlabs.gp.service.procedure.business.SearchProcedureBusinessHandler;
import br.com.nivlabs.gp.service.procedure.business.UpdateProcedureBusinessHandler;

/**
 * Camada de serviço para procedimentos e eventos clínicos/hospitalares
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Service
public class ProcedureService implements BaseService {

    @Autowired
    private SearchProcedureBusinessHandler searchProcedureBusinessHandler;
    @Autowired
    private CreateProcedureBusinessHandler createProcedureBusinessHandler;
    @Autowired
    private UpdateProcedureBusinessHandler updateProcedureBusinessHandler;
    @Autowired
    private DeleteProcedureBusinessHandler deleteProcedureBusinessHandler;

    /**
     * Realiza uma busca paginada de procedimentos
     * 
     * @param filters Filtros de busca de procedimentos
     * @param pageRequest Configurações de paginação
     * @return Página de procedimentos
     */
    public Page<ProcedureDTO> getResumedPage(ProcedureFilters filters, Pageable pageRequest) {
        return searchProcedureBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * Realiza uma busca de procedimento por identificador único do mesmo
     * 
     * @param id Identificador único do procedimento
     * @return Procedimento encontrado
     */
    public ProcedureInfoDTO findDTOById(Long id) {
        return searchProcedureBusinessHandler.byId(id);
    }

    /**
     * Cria um procedimento na aplicação
     * 
     * @param procedureInfo Informações do procedimento
     * @return Procedimento criado
     */
    public ProcedureInfoDTO create(ProcedureInfoDTO request) {
        return createProcedureBusinessHandler.create(request);
    }

    /**
     * Atualiza informações cadastrais de um procedimento
     * 
     * @param id Identificador único do procedimento
     * @param procedureInfo Procedimento atualizado
     * @return Procedimento atualizado
     */
    public ProcedureInfoDTO update(Long id, ProcedureInfoDTO procedureInfo) {
        return updateProcedureBusinessHandler.update(procedureInfo);
    }

    /**
     * Exclui um procedimento da aplicação
     * 
     * @param id Identificador único do procedimento
     */
    public void deleteById(Long id) {
        deleteProcedureBusinessHandler.byId(id);
    }

}
