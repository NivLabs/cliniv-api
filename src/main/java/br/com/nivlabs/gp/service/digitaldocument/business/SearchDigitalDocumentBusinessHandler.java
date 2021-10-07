package br.com.nivlabs.gp.service.digitaldocument.business;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DigitalDocument;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.repository.DigitalDocumentRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 *
 * Componente de negócio criado para realizar pesquisas de documentso digitais
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class SearchDigitalDocumentBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private DigitalDocumentRepository dao;

    /**
     * Busca informações de um documento digital por identificador único do documento
     * 
     * @param id Identificador único do documento
     * @return Documento digital
     */
    @Transactional
    public DigitalDocumentDTO byId(Long id) {
        DigitalDocument entity = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                "Documento digital com id %s não encontrado"));
        return convertDocument(entity);
    }

    /**
     * Converte a entidade relacional em objeto de transferência
     * 
     * @param entity Endidade relacional
     * @return Objeto de transferência
     */
    @Transactional
    private DigitalDocumentDTO convertDocument(DigitalDocument entity) {
        DigitalDocumentDTO response = new DigitalDocumentDTO();
        response.setId(entity.getId());
        response.setBase64(entity.getBase64());
        response.setCreatedAt(entity.getCreatedAt());
        response.setAttendanceEventId(entity.getAttendanceEvent().getId());
        response.setName(entity.getName());
        response.setType(entity.getType());
        return response;

    }

}
