package br.com.nivlabs.cliniv.service.person.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.enums.OperationType;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.PersonAddress;
import br.com.nivlabs.cliniv.models.domain.PersonDocument;
import br.com.nivlabs.cliniv.models.domain.PersonDocumentPK;
import br.com.nivlabs.cliniv.models.dto.AddressDTO;
import br.com.nivlabs.cliniv.models.dto.PersonInfoDTO;
import br.com.nivlabs.cliniv.repository.PersonDocumentRepository;
import br.com.nivlabs.cliniv.repository.PersonRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * Componente abstrato para criação ou atualização de cadastro de pessoas físicas
 *
 * @author viniciosarodrigues
 * @since 26-09-2021
 */
@Component
public abstract class CreateOrUpdatePersonBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected PersonDocumentRepository docRepo;
    @Autowired
    protected PersonRepository personRepo;

    /**
     * Process o endereço
     *
     * @param request      Objeto de transferência com informações da pessoa física
     * @param personEntity Objeto de entidade relacional de pessoa física
     */
    protected void addressProcess(PersonInfoDTO request, Person personEntity) {
        logger.info("Verificando endereço");
        if (request.getAddress() != null) {
            AddressDTO address = request.getAddress();
            PersonAddress addressEntity = new PersonAddress();

            if (personEntity.getAddress() != null) {
                logger.info("Endereço encontrado no cadastro de pessoa física. O Identificador único será reutilizado.");
                addressEntity.setId(personEntity.getAddress().getId());
            }
            addressEntity.setPerson(personEntity);
            addressEntity.setCity(address.getCity());
            addressEntity.setPostalCode(address.getPostalCode());
            addressEntity.setAddressNumber(address.getAddressNumber());
            addressEntity.setComplement(address.getComplement());
            addressEntity.setNeighborhood(address.getNeighborhood());
            addressEntity.setState(address.getState());
            addressEntity.setStreet(address.getStreet());

            personEntity.setAddress(addressEntity);
            logger.info("Endereço verificado.");
        } else {
            logger.info("Nenhum endereço informado, seguindo com o fluxo sem endereço...");
        }
    }

    /**
     * Processa documentos antes da persistência
     *
     * @param request Objeto de transferência com informações da pessoa física
     * @param entity  Objeto de entidade relacional de pessoa física
     */
    protected void documentsProcess(PersonInfoDTO request, Person entity) {
        logger.info("Iniciando processo de checagem de documentos...");
        if (entity != null) {
            logger.info("Removendo documentos anteriores...");
            docRepo.deleteByPerson(new Person(entity.getId()));
            docRepo.flush();

            if (request.getDocuments() != null && !request.getDocuments().isEmpty()) {
                logger.info("Adicionando novos documentos...");
                request.getDocuments().forEach(document -> {
                    PersonDocument convertedDoc = new PersonDocument();
                    convertedDoc.setDispatcher(document.getDispatcher());
                    convertedDoc.setExpeditionDate(document.getExpeditionDate());
                    convertedDoc.setValidate(document.getValidate());
                    convertedDoc.setUf(document.getUf());
                    convertedDoc.setPerson(new Person(entity.getId()));
                    convertedDoc.setId(new PersonDocumentPK(entity.getId(), document.getType(), document.getValue()));
                    docRepo.saveAndFlush(convertedDoc);
                    logger.info("Documento adicionado :: {}", document);
                });
            }
        }
        logger.info("Processamento de documentos finalizado com sucesso!");
    }

    /**
     * Realiza o parse de informações do objeto de transferência para o objeto de entidade relacional de pessoa física
     *
     * @param personInfo   Objeto de transferência com informações da pessoa física
     * @param personEntity Objeto de entidade relacional de pessoa física
     */
    protected void parsePropertiesToEntity(PersonInfoDTO personInfo, Person personEntity, OperationType operationType) {
        logger.info("Iniciando processo de conversão de DTO para entidade (PESSOA FÍSICA)");
        if (operationType == OperationType.CREATE) {
            logger.info("Operação de criação, o identificador será setado como nulo");
            personEntity.setId(null);
        } else if (operationType == OperationType.UPDATE) {
            logger.info("Operação de atualiza, o identificador será setado com o valor enviado nas informações à serem atualizadas :: {}",
                    personInfo.getId());
            personEntity.setId(personInfo.getId());
        }
        personEntity.setFullName(personInfo.getFullName());
        personEntity.setSocialName(personInfo.getSocialName());
        personEntity.setCpf(personInfo.getDocument().getValue());
        personEntity.setGender(personInfo.getGender());
        personEntity.setGenderIdentity(personInfo.getGenderIdentity());
        personEntity.setFatherName(personInfo.getFatherName());
        personEntity.setMotherName(personInfo.getMotherName());
        personEntity.setBornDate(personInfo.getBornDate());
        personEntity.setPrincipalNumber(personInfo.getPrincipalNumber());
        personEntity.setSecondaryNumber(personInfo.getSecondaryNumber());
        personEntity.setProfilePhoto(personInfo.getProfilePhoto());
        personEntity.setEmail(personInfo.getEmail());
        personEntity.setEthnicGroup(personInfo.getEthnicGroup());
        personEntity.setBloodType(personInfo.getBloodType());
        personEntity.setNationality(personInfo.getNationality());
    }

}
