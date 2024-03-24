package br.com.nivlabs.cliniv.service.report;

import br.com.nivlabs.cliniv.models.dto.*;
import br.com.nivlabs.cliniv.report.ReportParam;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.report.business.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Classe responsável por armazenar os documentos gerados por relatórios em base de dados
 *
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 21 de jun de 2020
 */
@Service
public class ReportService implements BaseService {

    private final SearchReportLayoutBusinessHandler searchReportLayoutBusinessHandler;
    private final CreateReportLayoutBusinessHandler createReportLayoutBusinessHandler;
    private final UpdateReportLayoutBusinessHandler updateReportLayoutBusinessHandler;
    private final DeleteReportLayoutBusinessHandler deleteReportLayoutBusinessHandler;

    private final GenerateReportBusinessHandler generateReportBusinessHandler;

    public ReportService(final SearchReportLayoutBusinessHandler searchReportLayoutBusinessHandler,
                         final CreateReportLayoutBusinessHandler createReportLayoutBusinessHandler,
                         final UpdateReportLayoutBusinessHandler updateReportLayoutBusinessHandler,
                         final DeleteReportLayoutBusinessHandler deleteReportLayoutBusinessHandler,
                         final GenerateReportBusinessHandler generateReportBusinessHandler) {
        this.searchReportLayoutBusinessHandler = searchReportLayoutBusinessHandler;
        this.createReportLayoutBusinessHandler = createReportLayoutBusinessHandler;
        this.updateReportLayoutBusinessHandler = updateReportLayoutBusinessHandler;
        this.deleteReportLayoutBusinessHandler = deleteReportLayoutBusinessHandler;
        this.generateReportBusinessHandler = generateReportBusinessHandler;
    }

    /**
     * Cria um documento digital anexado a um atendimento
     *
     * @param attendanceEventId Identificador único do atendimento
     * @param reportName        Nome do relatório
     * @param params            Parâmetros do relatório
     * @param reportInputStream Stream do Relatório (jxml)
     * @return Documento digital do relatório gerado
     */
    public DigitalDocumentDTO genareteDocumentFromJxmlStream(Long attendanceEventId, String reportName, boolean isActiveConnection, ReportParam params,
                                                             InputStream reportInputStream) {
        return generateReportBusinessHandler.generateFromJxmlStream(attendanceEventId, reportName, isActiveConnection, params, reportInputStream);
    }

    /**
     * Cria um documento digital anexado a um atendimento
     *
     * @param attendanceEventId Código identificador único do atendimento
     * @param title             Título do relatório
     * @param text              Texto Livre que será convertido em documento
     * @return Documento digital do relatório gerado
     */
    public DigitalDocumentDTO generateDocumentFromFormatedText(Long attendanceEventId, String title, String text) {
        return generateReportBusinessHandler.generateFromFormatedText(attendanceEventId, title, text);
    }

    /**
     * Cria relatório à partir de um layout pré-configurado
     *
     * @param id     Identificador único de Layout de Relatório
     * @param params Parâmetros do Layout
     * @return Documento digital gerado do relatório
     */
    public DigitalDocumentDTO generateDocumentFromReportLayout(Long id, boolean isActiveConnection, ReportGenerationRequestDTO params) {
        return generateReportBusinessHandler.generateFromLayout(id, isActiveConnection, params);
    }

    /**
     * Cria relatório à partir de um array de bytes de um JXML
     *
     * @param reportInputStream Array de bytes do relatório Jasper
     * @param name              Nome do relatório
     * @param params            Parâmetros do relatŕoio
     * @return Documento digital gerado do relatório
     */
    public DigitalDocumentDTO generateDocumentFromJxmlStream(InputStream reportInputStream, String name, boolean isActiveConnection, ReportParam params) {
        return generateReportBusinessHandler.generate(reportInputStream, name, isActiveConnection, params);
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
     * @param id   Identificador único do layout
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