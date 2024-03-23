package br.com.nivlabs.cliniv.service.responsible.business;

import br.com.nivlabs.cliniv.controller.filters.ResponsibleFilters;
import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.*;
import br.com.nivlabs.cliniv.models.dto.*;
import br.com.nivlabs.cliniv.repository.PersonRepository;
import br.com.nivlabs.cliniv.repository.ResponsibleRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Componente específico para consulta de profissionais e responsáveis
 *
 * @author viniciosarodrigues
 * @since 26-09-2021
 */
@Component
public class SearchResponsibleBusinessHandler implements BaseBusinessHandler {

    private static final String RESPONSIBLE_NOT_FOUND = "Responsável com o identificador %s não encontrado";

    @Autowired
    private Logger logger;

    @Autowired
    private ResponsibleRepository responsibleRepo;
    @Autowired
    private PersonRepository personRepo;

    /**
     * Realiza uma busca paginada por informações resumidas dos profissionais/responsáveis
     *
     * @param filters     Filtros de busca
     * @param pageRequest Configuraçõe de paginação
     * @return Página de responsáveis e profissionais
     */
    public Page<ResponsibleDTO> getPage(ResponsibleFilters filters) {
        return responsibleRepo.resumedList(filters);
    }

    /**
     * Busca um profissional ou responsável pelo identificador
     *
     * @param id Identificador único do responsável / profissional
     * @return Responsável / Profissional encontrado
     */
    @Transactional
    public ResponsibleInfoDTO byId(Long id) {
        Responsible responsibleFromDb = responsibleRepo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format(RESPONSIBLE_NOT_FOUND, id)));
        return convertEntityToDTO(responsibleFromDb);
    }

    /**
     * Busca um profissional ou responsável por CPF
     *
     * @param cpf CPF do profissional
     * @return Informações do profissional
     */
    @Transactional
    public ResponsibleInfoDTO byCPF(String cpf) {
        logger.info("Iniciando busca de profissional por CPF :: {}", cpf);
        try {
            if (StringUtils.isNullOrEmpty(cpf)) {
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "O CPF informado está nulo, informe um CPF para que a consulta possa ser realizada");
            }
            Responsible reponsible = responsibleRepo.findByCpf(cpf).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Profissional com o CPF %s não encontrado", cpf)));
            return convertEntityToDTO(reponsible);
        } catch (HttpException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                logger.warn(e.getMessage());
                logger.info("Iniciando busca de informações de pessoa física por CPF :: {}", cpf);
                Person personFromDb = personRepo.findByCpf(cpf)
                        .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Profissional não encontrado no CPF " + cpf));

                Responsible responsible = new Responsible();
                responsible.setPerson(personFromDb);

                return convertEntityToDTO(responsible);
            }
            throw e;
        }
    }

    /**
     * Converte entidade do modelo relacional em objeto de transferência
     *
     * @param responsible Entidade do modelo relacional
     * @return Objeto de transferência
     */
    @Transactional
    ResponsibleInfoDTO convertEntityToDTO(Responsible responsible) {
        logger.info("Iniciando processo de conversão de dados de entidade para objeto de transferência :: Responsible -> ResponsibleInfoDTO");
        Person person = responsible.getPerson();

        ResponsibleInfoDTO responsibleInfo = new ResponsibleInfoDTO();
        responsibleInfo.setId(responsible.getId());
        responsibleInfo.setFullName(person.getFullName());
        responsibleInfo.setSocialName(person.getSocialName());
        responsibleInfo.setBornDate(person.getBornDate());
        responsibleInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        responsibleInfo.setDocuments(convertDocuments(person.getDocuments()));
        responsibleInfo.setGender(person.getGender());
        responsibleInfo.setGenderIdentity(person.getGenderIdentity());
        responsibleInfo.setFatherName(person.getFatherName());
        responsibleInfo.setMotherName(person.getMotherName());
        responsibleInfo.setPrincipalNumber(person.getPrincipalNumber());
        responsibleInfo.setSecondaryNumber(person.getSecondaryNumber());
        responsibleInfo.setEmail(person.getEmail());
        responsibleInfo.setAddress(convertAddress(person.getAddress()));

        responsibleInfo.setProfilePhoto(person.getProfilePhoto());
        responsibleInfo.setCreatedAt(responsible.getCreatedAt());

        responsibleInfo.setEthnicGroup(person.getEthnicGroup());
        responsibleInfo.setBloodType(person.getBloodType());
        responsibleInfo.setNationality(person.getNationality());

        responsibleInfo.setProfessionalIdentity(new ProfessionalIdentityDTO(responsible.getInitialsIdentity(),
                responsible.getProfessionalIdentity()));
        responsibleInfo.setCreatedAt(responsibleInfo.getCreatedAt());
        responsibleInfo.setSpecializations(convertSpecializations(responsible.getSpecializations()));

        return responsibleInfo;
    }

    /**
     * Converte especialidade da entidade relacional para especialidade de objeto de transferência
     *
     * @param specializations Especializações da entidade relacional
     * @return Especializações de objetod e transferência
     */
    @Transactional
    List<SpecialityDTO> convertSpecializations(List<Speciality> specializations) {
        List<SpecialityDTO> listOfSpecializations = new ArrayList<>();
        logger.info("Verificando especializações...");
        if (specializations != null) {
            specializations.forEach(speciality -> listOfSpecializations.add(new SpecialityDTO(speciality.getId(), speciality.getName())));
        }
        return listOfSpecializations;
    }

    /**
     * Converte informações de endereço
     *
     * @param personAddress Endereço em entidade relacional
     * @return Endereço em objeto de transferência
     */
    @Transactional
    AddressDTO convertAddress(PersonAddress personAddress) {
        AddressDTO address = null;
        if (personAddress != null) {
            address = new AddressDTO();
            address.setAddressNumber(personAddress.getAddressNumber());
            address.setCity(personAddress.getCity());
            address.setComplement(personAddress.getComplement());
            address.setNeighborhood(personAddress.getNeighborhood());
            address.setPostalCode(personAddress.getPostalCode());
            address.setState(personAddress.getState());
            address.setStreet(personAddress.getStreet());
        }
        return address;
    }

    /**
     * Converte documentos de domínio para documentos de transferência
     *
     * @param documents Lista de documentos à serem convertidos
     * @return Lista de documentos convertidos
     */
    @Transactional
    List<DocumentDTO> convertDocuments(List<PersonDocument> documents) {
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
