package br.com.nivlabs.cliniv.service.sector.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Accommodation;
import br.com.nivlabs.cliniv.models.dto.AccommodationDTO;
import br.com.nivlabs.cliniv.repository.AccommodationRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para atualização cadastral de acomodações
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public class UpdateAccomodationBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected AccommodationRepository accommodationRepository;

    /**
     * Realiza a atualização cadastral de uma acomodação
     * 
     * @param accommodationInfo Novas informações da acomodação em questão à ser atualizada
     * @return Informações atualizadas da acomodação em questão
     */
    @Transactional
    public AccommodationDTO update(AccommodationDTO accommodationInfo) {
        if (accommodationInfo.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe o identificador único da acomodação à ser atualizada.");
        }
        Accommodation accommodationEntity = accommodationRepository.findById(accommodationInfo.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Acomodação (Sala | Leito) com o identificador %s não encontrado", accommodationInfo.getId())));

        logger.info("Iniciando processo de atualização cadastral de acomodação ATUAL :: ID: {} | Nome: {}", accommodationEntity.getId(),
                    accommodationEntity.getDescription());
        logger.info("Iniciando processo de atualização cadastral de acomodação NOVO  :: ID: {} | Nome: {}", accommodationInfo.getId(),
                    accommodationInfo.getDescription());

        accommodationEntity.setDescription(accommodationInfo.getDescription());
        accommodationEntity.setType(accommodationInfo.getType());

        accommodationRepository.save(accommodationEntity);

        logger.info("Atualização cadastral de acomodação realizada com sucesso!");

        return accommodationInfo;
    }

}
