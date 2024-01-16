package br.com.nivlabs.cliniv.service.sector.business;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Accommodation;
import br.com.nivlabs.cliniv.models.domain.Sector;
import br.com.nivlabs.cliniv.models.dto.AccommodationDTO;
import br.com.nivlabs.cliniv.repository.AccommodationRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * Componente específico para criação de acomodações no sistema
 * 
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public class CreateAccomodationBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected AccommodationRepository accommodationRepository;
    @Autowired
    protected SearchSectorBusinessHandler searchSectorBusinessHandler;

    /**
     * Realiza o cadastro de uma nova acomodação ao setor
     * 
     * @param request Informações da acomodação
     * @return Acomodação criada
     */
    @Transactional
    public AccommodationDTO create(AccommodationDTO accommodationInfo) {
        if (accommodationInfo.getSectorId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Informe o identificador do setor para a criação de uma nova acomodação");
        }

        logger.info("Verificando se o setor informado existe :: ID Setor: {}", accommodationInfo.getSectorId());
        var sectorInfo = searchSectorBusinessHandler.byId(accommodationInfo.getSectorId());

        accommodationInfo.setId(null);
        logger.info("Iniciando processo de atualização cadastral de acomodação NOVO  :: Acomodação: {} | Setor: {}",
                    accommodationInfo.getDescription(), sectorInfo.getDescription());

        Accommodation accommodationEntity = new Accommodation();
        accommodationEntity.setSector(new Sector(sectorInfo.getId()));
        accommodationEntity.setDescription(accommodationInfo.getDescription());
        accommodationEntity.setType(accommodationInfo.getType());

        accommodationEntity = accommodationRepository.save(accommodationEntity);

        accommodationInfo.setId(accommodationEntity.getId());

        logger.info("Criação de acomodação realizada com sucesso!");

        return accommodationInfo;
    }

}
