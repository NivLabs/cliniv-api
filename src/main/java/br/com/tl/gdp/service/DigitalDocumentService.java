package br.com.tl.gdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tl.gdp.exception.ObjectNotFoundException;
import br.com.tl.gdp.models.domain.DigitalDocument;
import br.com.tl.gdp.models.dto.DigitalDocumentDTO;
import br.com.tl.gdp.repository.DigitalDocumentRepository;

/**
 * Classe PersonAddressService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class DigitalDocumentService implements GenericService<DigitalDocument, Long> {

    @Autowired
    private DigitalDocumentRepository dao;

    public DigitalDocumentDTO findDtoById(Long id) {
        return findById(id).getDtoFromDomain();
    }

    @Override
    public DigitalDocument findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Documento digital com id %s não encontrado"));
    }
}
