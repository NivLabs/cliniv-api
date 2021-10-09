package br.com.nivlabs.gp.service.speciality.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.Speciality;
import br.com.nivlabs.gp.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.gp.repository.SpecialityRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente base para criação de cadastro de informações de especialidades
 *
 * @author viniciosarodrigues
 * @since 07-10-2021
 *
 */
@Component
public class CreateSpecialityBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected SpecialityRepository specialityRepository;

    /**
     * Cria um novo cadastro de especialidade
     * 
     * @param specialityInfo Informações da especialidade à ser criada
     * @return Especialidade criada
     */
    @Transactional
    public SpecialityInfoDTO create(SpecialityInfoDTO specialityInfo) {
        logger.info("Iniciando processo de cadastro de especialidade...");
        Speciality specialityEntity = new Speciality();
        specialityEntity.setDescription(specialityInfo.getDescription());
        specialityEntity.setName(specialityInfo.getName());
        specialityRepository.save(specialityEntity);

        logger.info("Especialidade cadastrada com sucesso :: {}", specialityInfo);

        specialityInfo.setId(specialityEntity.getId());

        return specialityInfo;
    }

}
