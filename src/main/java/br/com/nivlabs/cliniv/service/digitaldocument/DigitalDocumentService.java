package br.com.nivlabs.cliniv.service.digitaldocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.digitaldocument.business.CreateDigitalDocumentBusinessHandler;
import br.com.nivlabs.cliniv.service.digitaldocument.business.SearchDigitalDocumentBusinessHandler;

/**
 *
 * Camada de serviços para operações com documentos digitais da aplicação
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Service
public class DigitalDocumentService implements BaseService {

    @Autowired
    SearchDigitalDocumentBusinessHandler searchDigitalDocumentBusinessHandler;
    @Autowired
    CreateDigitalDocumentBusinessHandler createDigitalDocumentBusinessHandler;

    /**
     * Cria um documento digital na aplicação
     * 
     * @param request Requisição de criação de documento digital
     * @return Documento digital criado
     */
    public DigitalDocumentDTO createDocument(DigitalDocumentDTO request) {
        return createDigitalDocumentBusinessHandler.create(request);
    }

    /**
     * Requisição de consulta de documento por identificador único do documento
     * 
     * @param id Identificador único do documento
     * @return Documento digital encontrado
     */
    public DigitalDocumentDTO findById(Long id) {
        return searchDigitalDocumentBusinessHandler.byId(id);
    }

}
