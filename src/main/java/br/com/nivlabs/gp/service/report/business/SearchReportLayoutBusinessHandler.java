package br.com.nivlabs.gp.service.report.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.ReportLayout;
import br.com.nivlabs.gp.models.domain.ReportLayoutParameter;
import br.com.nivlabs.gp.models.dto.ReportLayoutDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutInfoDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutParameterDTO;
import br.com.nivlabs.gp.repository.ReportRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para consulta de layouts de relatórios
 *
 * @author viniciosarodrigues
 * @since 10-10-2021
 *
 */
@Component
public class SearchReportLayoutBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected ReportRepository reportRepository;

    /**
     * Busca lista paginada de Layouts de relatórios
     * 
     * @param pageSettings Configurações de paginação
     * @return Página de layouts
     */
    @Transactional
    public Page<ReportLayoutDTO> getPage(Pageable pageSettings) {
        logger.info("Iniciando busca paginada de layoyts...");
        Page<ReportLayout> page = reportRepository.findAll(pageSettings);
        List<ReportLayoutDTO> newPage = new ArrayList<>();
        page.getContent().forEach(reportLayoutEntity -> {
            ReportLayoutDTO reportLayoutInfo = new ReportLayoutDTO();
            reportLayoutInfo.setId(reportLayoutEntity.getId());
            reportLayoutInfo.setCreatedAt(reportLayoutEntity.getCreatedAt());
            reportLayoutInfo.setDescription(reportLayoutEntity.getDescription());
            reportLayoutInfo.setName(reportLayoutEntity.getName());
            newPage.add(reportLayoutInfo);
        });
        logger.info("Busca concluída, devolvendo um total de {} itens encontrados.", page.getTotalElements());
        return new PageImpl<>(newPage, pageSettings, page.getTotalElements());
    }

    /**
     * Busca um layout de relatório por identificador único
     * 
     * @param id Identificador único do layout de relatório
     * @return Layout de relatório encontrado
     */
    @Transactional
    public ReportLayoutInfoDTO byId(Long id) {
        ReportLayout reportLayoutEntity = reportRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Layout com o identificador %s não encontrado!", id)));

        ReportLayoutInfoDTO reportLayoutInfo = new ReportLayoutInfoDTO();

        reportLayoutInfo.setId(reportLayoutEntity.getId());
        reportLayoutInfo.setCreatedAt(reportLayoutEntity.getCreatedAt());
        reportLayoutInfo.setDescription(reportLayoutEntity.getDescription());
        reportLayoutInfo.setName(reportLayoutEntity.getName());
        reportLayoutInfo.setBase64(reportLayoutEntity.getXml());

        List<ReportLayoutParameterDTO> listParam = new ArrayList<>();
        if (reportLayoutEntity.getParams() != null) {
            for (ReportLayoutParameter parameterEntity : reportLayoutEntity.getParams()) {
                ReportLayoutParameterDTO parameterInfo = new ReportLayoutParameterDTO();
                parameterInfo.setId(parameterEntity.getId());
                parameterInfo.setDescription(parameterEntity.getDescription());
                parameterInfo.setDefaultValue(parameterEntity.getDefaultValue());
                parameterInfo.setName(parameterEntity.getName());
                parameterInfo.setType(parameterEntity.getType());
                listParam.add(parameterInfo);
            }
            reportLayoutInfo.setParams(listParam);
        }
        return reportLayoutInfo;
    }
}
