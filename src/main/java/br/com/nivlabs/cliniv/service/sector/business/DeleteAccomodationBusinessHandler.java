package br.com.nivlabs.cliniv.service.sector.business;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Accommodation;
import br.com.nivlabs.cliniv.repository.AccommodationRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * Componente específico para exclusão de acomodações
 * 
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public class DeleteAccomodationBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected AccommodationRepository accommodationRepository;

    /**
     * Deleta acomodação por identificador único da acomodação
     * 
     * @param id Identificador único da acomodação
     */
    @Transactional
    public void byId(Long id) {
        if (id == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe o identificador da acomodação para a exclusão da mesma");
        }
        logger.info("Iniciando processo de exclusão de acomodação :: ID: {}", id);
        Accommodation accommodationEntity = accommodationRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format(
                                                                                         "Acomodação com o identificado %s não encontrado, não é possível deletar um registro inexistente.",
                                                                                         id)));
        logger.info("Acomodação à ser excluída :: {}", accommodationEntity.getDescription());
        accommodationRepository.delete(accommodationEntity);
        logger.info("Acomodação excluída com sucesso!");

    }

}
