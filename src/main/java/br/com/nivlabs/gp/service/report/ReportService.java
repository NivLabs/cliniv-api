package br.com.nivlabs.gp.service.report;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.ReportGenerationRequestDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.report.business.CreateReportLayoutBusinessHandler;
import br.com.nivlabs.gp.service.report.business.DeleteReportLayoutBusinessHandler;
import br.com.nivlabs.gp.service.report.business.GenerateReportBusinessHandler;
import br.com.nivlabs.gp.service.report.business.SearchReportLayoutBusinessHandler;
import br.com.nivlabs.gp.service.report.business.UpdateReportLayoutBusinessHandler;

/**
 * 
 * Classe responsável por armazenar os documentos gerados por relatórios em base de dados
 * 
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 21 de jun de 2020
 *
 *
 */
@Service
public class ReportService implements BaseService {

    @Autowired
    private SearchReportLayoutBusinessHandler searchReportLayoutBusinessHandler;
    @Autowired
    private CreateReportLayoutBusinessHandler createReportLayoutBusinessHandler;
    @Autowired
    private UpdateReportLayoutBusinessHandler updateReportLayoutBusinessHandler;
    @Autowired
    private DeleteReportLayoutBusinessHandler deleteReportLayoutBusinessHandler;

    @Autowired
    private GenerateReportBusinessHandler generateReportBusinessHandler;

    /**
     * Cria um documento digital anexado a um atendimento
     * 
     * @param attendanceEventId Identificador único do atendimento
     * @param reportName Nome do relatório
     * @param params Parâmetros do relatório
     * @param reportInputStream Stream do Relatório (jxml)
     * @return Documento digital do relatório gerado
     */
    public DigitalDocumentDTO genareteDocumentFromJxmlStream(Long attendanceEventId, String reportName, ReportParam params,
                                                             InputStream reportInputStream) {
        return generateReportBusinessHandler.generateFromJxmlStream(attendanceEventId, reportName, params, reportInputStream);
    }

    /**
     * Cria um documento digital anexado a um atendimento
     * 
     * @param attendanceEventId Código identificador único do atendimento
     * @param title Título do relatório
     * @param text Texto Livre que será convertido em documento
     * @return Documento digital do relatório gerado
     */
    public DigitalDocumentDTO generateDocumentFromFormatedText(Long attendanceEventId, String title, String text) {
        return generateReportBusinessHandler.generateFromFormatedText(attendanceEventId, title, text);
    }

    /**
     * Cria relatório à partir de um layout pré-configurado
     * 
     * @param id Identificador único de Layout de Relatório
     * @param params Parâmetros do Layout
     * @return Documento digital gerado do relatório
     */
    public DigitalDocumentDTO generateDocumentFromReportLayout(Long id, ReportGenerationRequestDTO params) {
        return generateReportBusinessHandler.generateFromLayout(id, params);
    }

    /**
     * Busca um layout de relatório por identificador único
     * 
     * @param id Identificador único do layout de relatório
     * @return Layout de relatório encontrado
     */
    public ReportLayoutInfoDTO findReportLayoutById(Long id) {
        return searchReportLayoutBusinessHandler.byId(id);
    }

    /**
     * Busca lista paginada de Layouts de relatórios
     * 
     * @param pageSettings Configurações de paginação
     * @return Página de layouts
     */
    public Page<ReportLayoutDTO> findPageOfReportLayout(Pageable pageSettings) {
        return searchReportLayoutBusinessHandler.getPage(pageSettings);
    }

    /**
     * Cria o cadastro de um layout de relatório na aplicação
     * 
     * @param file Arquivo do Layout de relatório
     * @return Layout cadastrado
     */
    public ReportLayoutInfoDTO newReporLayout(FileDTO file) {
        return createReportLayoutBusinessHandler.create(file);
    }

    /**
     * Atualiza o cadastro de um layout de relatório na aplicação
     * 
     * @param Long id Identificador único do layout
     * @param file Arquivo do Layout de relatório
     * @return Layout cadastrado
     */
    public ReportLayoutInfoDTO update(Long id, FileDTO file) {
        return updateReportLayoutBusinessHandler.update(id, file);
    }

    /**
     * Deleta um layout baseado no identificador único do Layout de relatório
     * 
     * @param id Identificador único do Layout de relatório
     */
    public void deleteLayoutById(Long id) {
        deleteReportLayoutBusinessHandler.byId(id);
    }

}