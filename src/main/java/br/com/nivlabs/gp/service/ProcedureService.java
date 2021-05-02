/**
 * 
 */
package br.com.nivlabs.gp.service;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ProcedureFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.domain.tiss.Procedure_;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.dto.ProcedureInfoDTO;
import br.com.nivlabs.gp.repository.ProcedureRepository;

/**
 * Camada de serviço para procedimentos e eventos clínicos/hospitalares
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ProcedureService implements GenericService {

    @Autowired
    private ProcedureRepository dao;
    @Autowired
    private Logger logger;

    public Page<ProcedureDTO> getResumedPage(ProcedureFilters filters, Pageable pageRequest) {
        return dao.resumedList(filters, pageRequest);
    }

    public ProcedureInfoDTO findDTOById(Long id) {
        return findById(id).getDTO();
    }

    public Procedure findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Procedimento com código %s não encontrado!", id)));
    }

    public ProcedureInfoDTO update(Long id, ProcedureInfoDTO request) {
        logger.info("Iniciando processo de atualização de procedimento :: {}", id);
        Procedure procedureOrEvent = findById(id);
        logger.info("Procedimento encontrado :: {}", procedureOrEvent.getDescription());
        BeanUtils.copyProperties(request, procedureOrEvent, Procedure_.ID);
        procedureOrEvent = dao.saveAndFlush(procedureOrEvent);
        logger.info("Atualização concluída com sucesso!");

        return request;
    }

    public void deleteById(Long id) {
        logger.info("Iniciando processo de exclusão de procedimento por identificador :: {}", id);
        Procedure procedureOrEvent = findById(id);
        logger.info("Procedimento que será excluído :: {}", procedureOrEvent.getDescription());
        dao.delete(procedureOrEvent);
        logger.info("Procedimento excluído com sucesso!");
    }

    public ProcedureInfoDTO persist(ProcedureInfoDTO request) {
        logger.info("Iniciando a criação de um novo procedimento :: {}", request.getDescription());
        Procedure entity = new Procedure();
        BeanUtils.copyProperties(request, entity, Procedure_.ID);
        dao.saveAndFlush(entity);
        request.setId(entity.getId());
        logger.info("Criação do novo procedimento realizada com sucesso :: {} | {}", request.getId(), request.getDescription());
        return request;
    }

}
