package br.com.nivlabs.cliniv.service.speciality.business;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.controller.filters.SpecialityFilter;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.domain.Speciality;
import br.com.nivlabs.cliniv.models.dto.ResponsibleDTO;
import br.com.nivlabs.cliniv.models.dto.SpecialityDTO;
import br.com.nivlabs.cliniv.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.cliniv.repository.SpecialityRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para consulta de especialidades
 *
 * @author viniciosarodrigues
 * @since 07-10-2021
 *
 */
@Component
public class SearchSpecialityBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected SpecialityRepository specialityRepository;

    /**
     * Busca infomações paginadas de especialidades cadastradas no sistema
     * 
     * @param filter Filtros de busca
     * @param pageSettings Configurações de paginação
     * @return Informações paginadas de especialidades cadastradas no sistema
     */
    public Page<SpecialityDTO> getPage(SpecialityFilter filter) {
        logger.info("Iniciando processo de busca paginada de especialidades :: {}", filter);
        return specialityRepository.resumedList(filter);
    }

    /**
     * Busca informações detalhadas de uma especialidade baseada no identificador único da especialidade
     * 
     * @param id Identificador único da especialidade
     * @return Informações detalhadas da especialidade
     */
    @Transactional
    public SpecialityInfoDTO byId(Long id) {
        logger.info("Inicando busca de especialidade por ID :: {}", id);
        Speciality specialityEntity = specialityRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Especialidade com identificador %s não encontrada", id)));

        return convertSpecialityEntityToDTO(specialityEntity);
    }

    /**
     * Converte Entidade Relacional de especialidade em DTO
     * 
     * @param specialityEntity Entidade relacional de especialidade
     * @return DTO de especialidade
     */
    @Transactional
    private SpecialityInfoDTO convertSpecialityEntityToDTO(Speciality specialityEntity) {
        logger.info("Convertendo especialidade encontrada...");
        SpecialityInfoDTO specialityInfo = new SpecialityInfoDTO();
        specialityInfo.setId(specialityEntity.getId());
        specialityInfo.setName(specialityEntity.getName());
        specialityInfo.setDescription(specialityEntity.getDescription());

        specialityEntity.getResponsibles().forEach(responsible -> {
            ResponsibleDTO responsibleDTO = convertResponsibleEntityToDTO(responsible);
            specialityInfo.getResponsibles().add(responsibleDTO);
        });

        logger.info("Especialidade convertida :: {}", specialityInfo);
        return specialityInfo;
    }

    /**
     * Converte entidade relacional de profissional para DTO
     * 
     * @param responsible Entidade relacional de profissional
     * @return DTO de profissional
     */
    private ResponsibleDTO convertResponsibleEntityToDTO(Responsible responsible) {
        Person personEntity = responsible.getPerson();

        ResponsibleDTO responsibleDTO = new ResponsibleDTO();
        responsibleDTO.setId(responsible.getId());
        responsibleDTO.setProfessionalIdentity(responsible.getProfessionalIdentity());
        responsibleDTO.setInitialsIdentity(responsible.getInitialsIdentity());

        responsibleDTO.setFullName(personEntity.getFullName());
        responsibleDTO.setSocialName(personEntity.getSocialName());
        responsibleDTO.setCpf(personEntity.getCpf());
        responsibleDTO.setBornDate(personEntity.getBornDate());
        responsibleDTO.setPrincipalNumber(personEntity.getPrincipalNumber());
        responsibleDTO.setGender(personEntity.getGender());

        return responsibleDTO;
    }
}
