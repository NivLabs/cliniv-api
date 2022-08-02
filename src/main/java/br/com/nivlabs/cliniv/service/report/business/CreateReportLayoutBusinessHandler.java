package br.com.nivlabs.cliniv.service.report.business;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.ReportLayout;
import br.com.nivlabs.cliniv.models.domain.ReportLayoutParameter;
import br.com.nivlabs.cliniv.models.dto.FileDTO;
import br.com.nivlabs.cliniv.models.dto.ReportLayoutInfoDTO;

/**
 * 
 * Componente específico para cadastro de layout de relatório na aplicação
 *
 * @author viniciosarodrigues
 * @since 10-10-2021
 *
 */
@Component
public class CreateReportLayoutBusinessHandler extends CreateOrUpdateReportLayoutBusinessHandler {

    /**
     * Cria o cadastro de um layout de relatório na aplicação
     * 
     * @param file Arquivo do Layout de relatório
     * @return Layout cadastrado
     */
    @Transactional
    public ReportLayoutInfoDTO create(FileDTO file) {
        logger.info("Iniciando processo de criação de layout de relatório...");
        ReportLayoutInfoDTO reportLayoutDTO = new ReportLayoutInfoDTO();
        ReportLayout reportLayout = new ReportLayout();
        reportLayout.setId(null);
        reportLayout.setName(file.getName());
        reportLayout.setDescription(file.getName());
        reportLayout.setXml(file.getBase64());
        reportLayout.setCreatedAt(LocalDateTime.now());

        logger.info("Persistindo layout na base de dados...");
        reportRepository.save(reportLayout);
        reportLayoutDTO.setId(reportLayout.getId());
        logger.info("Layout criado, iniciando persistência de parâmetros...");
        try {

            List<ReportLayoutParameter> params = readParamsXml(file.getBase64(), reportLayoutDTO.getId());
            parameterRepository.saveAll(params);
            parameterRepository.flush();
            reportLayoutDTO.setParams(convertParams(params));
            logger.info("Parâmetros salvos, devolvendo resposta para o canal de requisição.");

            return reportLayoutDTO;
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na criação do relatório. Entre em contato com o suporte!");
        }
    }
}
