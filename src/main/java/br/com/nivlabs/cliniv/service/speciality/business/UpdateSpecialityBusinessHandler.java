package br.com.nivlabs.cliniv.service.speciality.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Speciality;
import br.com.nivlabs.cliniv.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.cliniv.repository.SpecialityRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * Componente específico para atualização de informações de especialidades
 * 
 *
 * @author viniciosarodrigues
 * @since 07-10-2021
 *
 */
@Component
public class UpdateSpecialityBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected SpecialityRepository specialityRepository;

    /**
     * Atualiza informações de uma especialidade
     * 
     * @param specialityInfo Informações da especialidade
     * @return Especialidade atualizada
     */
    @Transactional
    public SpecialityInfoDTO update(SpecialityInfoDTO specialityInfo) {
        if (specialityInfo.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Nenhum identificador enviado para atualização de especialidade");
        }
        logger.info("Iniciando processo de atualização cadastral de especialidade :: {}", specialityInfo.getId());
        Speciality specialityEntity = specialityRepository.findById(specialityInfo.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Especialidade com identificador %s não encontrada", specialityInfo.getId())));

        specialityEntity.setDescription(specialityInfo.getDescription());
        specialityEntity.setName(specialityInfo.getName());

        specialityRepository.save(specialityEntity);
        logger.info("Atualização cadastro realizada com sucesso :: {}", specialityInfo);

        return specialityInfo;
    }

}
