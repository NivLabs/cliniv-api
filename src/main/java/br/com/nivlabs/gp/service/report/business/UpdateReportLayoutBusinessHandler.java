package br.com.nivlabs.gp.service.report.business;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.ReportLayout;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutInfoDTO;

/**
 * 
 * Componente específico para atualização cadastral de layouts de relatórios
 *
 * @author viniciosarodrigues
 * @since 11-10-2021
 *
 */
@Component
public class UpdateReportLayoutBusinessHandler extends CreateOrUpdateReportLayoutBusinessHandler {

    /**
     * Atualiza o cadastro de um layout de relatório na aplicação
     * 
     * @param Long id Identificador único do layout
     * @param file Arquivo do Layout de relatório
     * @return Layout cadastrado
     */
    @Transactional
    public ReportLayoutInfoDTO update(Long id, FileDTO file) {
        logger.info("Buscando layout do relatório...");
        ReportLayout reportLayoutEntity = reportRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Layout com o identificador %s não encontrado!", id)));
        logger.info("Layout encontrado :: {}", reportLayoutEntity.getName());
        logger.info("Iniciando remoção dos antigos parâmetros...");
        parameterRepository.deleteByLayout(new ReportLayout(id));
        parameterRepository.flush();
        logger.info("Remoção concluída com sucesso!");
        logger.info("Iniciando criação dos novos parâmetros...");
        try {
            reportLayoutEntity.setName(file.getName());
            reportLayoutEntity.setParams(readParamsXml(file.getBase64(), id));
            reportLayoutEntity.setXml(file.getBase64());
            parameterRepository.saveAll(reportLayoutEntity.getParams());
            reportRepository.saveAndFlush(reportLayoutEntity);
            ReportLayoutInfoDTO reportLayoutInfo = new ReportLayoutInfoDTO();
            reportLayoutInfo.setId(id);
            reportLayoutInfo.setBase64(file.getBase64());
            reportLayoutInfo.setCreatedAt(reportLayoutEntity.getCreatedAt());
            reportLayoutInfo.setName(reportLayoutEntity.getName());
            reportLayoutInfo.setParams(convertParams(reportLayoutEntity.getParams()));
            return reportLayoutInfo;
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao atualizar o relatório... Favor entrar em contato com o suporte.");
        }
    }
}
