package br.com.nivlabs.gp.service.person.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.PersonDocument;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.repository.PersonRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * 
 * Componente específico para consulta de pessoas
 *
 * @author viniciosarodrigues
 * @since 26-09-2021
 *
 */
@Component
public class SearchPersonBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private PersonRepository personRepo;

    /**
     * Consulta informações de pessoa física por identificador único do cadastro de pessoa física
     * 
     * @param id Identificador único de pessoa física
     * @return Informações detalhadas do cadastro de pessoa física encontrado
     */
    @Transactional
    public PersonInfoDTO byId(Long id) {
        logger.info("Iniciando consulta de pessoa por ID :: {}", id);
        Person person = personRepo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Pessoa com o identificador %s não encontrado", id)));

        return convertEntityToDTO(person);

    }

    /**
     * Consulta informações de pessoa física por CPF do cadastro de pessoa física
     * 
     * @param cpf CPF de pessoa física
     * @return Informações detalhadas do cadastro de pessoa física encontrado
     */
    @Transactional
    public PersonInfoDTO byCPF(String cpf) {
        if (StringUtils.isNullOrEmpty(cpf)) {
            throw new HttpException(HttpStatus.NOT_FOUND,
                    "O CPF informado é nulo, informe um CPF para que a consulta possa ser realizada");
        }
        PersonInfoDTO response = convertEntityToDTO(personRepo.findByCpf(cpf).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Pessoa com CPF %s não encontrado", cpf))));
        personRepo.flush();
        return response;
    }

    @Transactional
    private PersonInfoDTO convertEntityToDTO(Person person) {
        logger.info("Iniciando processo de conversão de dados de entidade para objeto de transferência :: Person -> PersonInfoDTO");

        PersonInfoDTO response = new PersonInfoDTO();
        response.setId(person.getId());
        response.setFullName(person.getFullName());
        response.setSocialName(person.getSocialName());
        response.setBornDate(person.getBornDate());
        response.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        response.setDocuments(convertDocuments(person.getDocuments()));
        response.setGender(person.getGender());
        response.setGenderIdentity(person.getGenderIdentity());
        response.setFatherName(person.getFatherName());
        response.setMotherName(person.getMotherName());
        response.setPrincipalNumber(person.getPrincipalNumber());
        response.setSecondaryNumber(person.getSecondaryNumber());
        response.setEmail(person.getEmail());

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            address.setAddressNumber(person.getAddress().getAddressNumber());
            address.setCity(person.getAddress().getCity());
            address.setComplement(person.getAddress().getComplement());
            address.setNeighborhood(person.getAddress().getNeighborhood());
            address.setPostalCode(person.getAddress().getPostalCode());
            address.setState(person.getAddress().getState());
            address.setStreet(person.getAddress().getStreet());
            response.setAddress(address);
        }

        response.setProfilePhoto(person.getProfilePhoto());

        response.setEthnicGroup(person.getEthnicGroup());
        response.setBloodType(person.getBloodType());
        response.setNationality(person.getNationality());

        return response;
    }

    /**
     * Converte documentos de domínio para documentos de transferência
     * 
     * @param documents Lista de documentos à serem convertidos
     * @return Lista de documentos convertidos
     */
    @Transactional
    private List<DocumentDTO> convertDocuments(List<PersonDocument> documents) {
        List<DocumentDTO> convertedDocuments = new ArrayList<>();

        documents.forEach(doc -> {
            DocumentDTO convertedDoc = new DocumentDTO();
            BeanUtils.copyProperties(doc, convertedDoc);
            convertedDoc.setPersonId(doc.getId().getPersonId());
            convertedDoc.setType(doc.getId().getType());
            convertedDoc.setValue(doc.getId().getValue());
            convertedDocuments.add(convertedDoc);
        });

        return convertedDocuments;
    }

}
