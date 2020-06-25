package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DigitalDocument;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.repository.DigitalDocumentRepository;

/**
 * Classe PersonAddressService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class DigitalDocumentService implements GenericService {

    @Autowired
    private DigitalDocumentRepository dao;

    public DigitalDocumentDTO persist(DigitalDocumentDTO request) {
        DigitalDocument document = new DigitalDocument();
        BeanUtils.copyProperties(request, document, "id");
        dao.save(document);
        return document.getDtoFromDomain();
    }

    public DigitalDocumentDTO findDtoById(Long id) {
        return findById(id).getDtoFromDomain();
    }

    public DigitalDocument findById(Long id) {
        return dao.findById(id).orElseThrow(
                                            () -> new HttpException(HttpStatus.NOT_FOUND, "Documento digital com id %s não encontrado"));
    }
}
