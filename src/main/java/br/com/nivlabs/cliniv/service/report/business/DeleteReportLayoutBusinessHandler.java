package br.com.nivlabs.cliniv.service.report.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.ReportLayout;
import br.com.nivlabs.cliniv.repository.ReportRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para exclusão de Layout de Relatório
 *
 * @author viniciosarodrigues
 * @since 12-10-2021
 *
 */
@Component
public class DeleteReportLayoutBusinessHandler implements BaseBusinessHandler {
    @Autowired
    protected Logger logger;
    @Autowired
    protected ReportRepository reportRepository;

    /**
     * Deleta um layout baseado no identificador único do Layout de relatório
     * 
     * @param id Identificador único do Layout de relatório
     */
    public void byId(Long id) {
        ReportLayout reportLayoutEntity = reportRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Layout com o identificador %s não encontrado!", id)));
        logger.info("Layout encontrado :: {}", reportLayoutEntity.getName());
        reportRepository.deleteById(id);
    }

}
