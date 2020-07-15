package br.com.nivlabs.gp.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.models.dto.PrescriptionInfoDTO;

/**
 * Camada de serviço para manipulação de prescrição médica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class PrescriptionService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Cria uma nova prescrição médica do paciente
     * 
     * @param request
     * @return
     */
    public PrescriptionInfoDTO persist(PrescriptionInfoDTO request, String username) {
        logger.info("Iniciando a criação de uma nova prescrição médica para o atendimento {}", request.getAttendanceId());

        LocalDateTime now = LocalDateTime.now();
        logger.info("Adicionando data/hora da criação da prescrição | {}", now);
        request.setCreatedAt(now);

        logger.info("Iniciando criação do documento digital da prescrição");

        return null;
    }
}
