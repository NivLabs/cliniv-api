package br.com.nivlabs.cliniv.service.digitaldocument.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.AttendanceEvent;
import br.com.nivlabs.cliniv.models.domain.DigitalDocument;
import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.repository.DigitalDocumentRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente de negócio para processo de criação de documento digital na aplicação
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class CreateDigitalDocumentBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private DigitalDocumentRepository digtalDocRepo;

    /**
     * Cria um documento digital na aplicação
     * 
     * @param request Objeto de requisição para criação de documento digital
     * @return Documento criado na base
     */
    public DigitalDocumentDTO create(DigitalDocumentDTO request) {
        if (request.getAttendanceEventId() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é possível inserir um documento de evento de atendimento");
        DigitalDocument document = new DigitalDocument();
        document.setBase64(request.getBase64());
        document.setName(request.getName());
        document.setType(request.getType());
        document.setCreatedAt(request.getCreatedAt());
        document.setAttendanceEvent(new AttendanceEvent(request.getAttendanceEventId()));

        document = digtalDocRepo.save(document);

        return convertDocument(document);
    }

    /**
     * Converte a entidade relacional em objeto de transferência
     * 
     * @param entity Endidade relacional
     * @return Objeto de transferência
     */
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
