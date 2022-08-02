package br.com.nivlabs.cliniv.service.attendance.prescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.PrescriptionInfoDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.attendance.prescription.business.CreatePrescriptionBusinessHandler;

/**
 * Camada de serviço para manipulação de prescrição médica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class PrescriptionService implements BaseService {

    @Autowired
    private CreatePrescriptionBusinessHandler createPrescriptionBusinessHandler;

    /**
     * Cria uma nova prescrição médica do paciente
     * 
     * @param request Informações da prescrição
     */
    public void createPrescription(PrescriptionInfoDTO request) {
        createPrescriptionBusinessHandler.createPrescription(request);
    }

}
