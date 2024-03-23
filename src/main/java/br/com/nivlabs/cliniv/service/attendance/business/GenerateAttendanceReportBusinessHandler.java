package br.com.nivlabs.cliniv.service.attendance.business;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.models.dto.InstituteDTO;
import br.com.nivlabs.cliniv.models.dto.ReportParametersDTO;
import br.com.nivlabs.cliniv.report.ReportParam;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.InstituteService;
import br.com.nivlabs.cliniv.service.report.ReportService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class GenerateAttendanceReportBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    private ReportService reportService;
    @Autowired
    private InstituteService instituteService;

    private static final String GENERIC_REPORT_SOURCE = "reports/attendance_report.jrxml";
    private static final String HOSPITAL_LOGO = "HOSPITAL_LOGO";

    @Transactional
    public DigitalDocumentDTO generate(ReportParametersDTO reportParameters) {

        logger.info("Solicitação de criação do relatório de atendimento recebida... Parâmetros :: {}", reportParameters);

        var month = reportParameters.getMonth();
        int year = reportParameters.getYear();
        var responsible = reportParameters.getResponsibleId();
        if (month != null && (month < 1 || month > 12)) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O mês de vigência informado para a geração do relatório de atendimento é inválido. Informe apenas valores numéricos de 1 a 12");
        } else if (year > LocalDateTime.now().getYear()) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O ano de vigência informado para a geração do relatório de atendimento é inválido. Informe um ano inferior ou igual ao ano atual.");
        }
        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        InstituteDTO instituteDTO = instituteService.getSettings();
        String logoBase64 = instituteDTO.getCustomerInfo().getLogoBase64();

        ReportParam params = new ReportParam();
        params.getParams().put(HOSPITAL_LOGO, logoBase64);
        params.getParams().put("ID_PROFISSIONAL", responsible);
        params.getParams().put("MES_VIGENCIA", month);
        params.getParams().put("ANO_VIGENCIA", year);

        return generateFromJxmlStream(params);
    }

    /**
     * Cria um documento digital anexado a um atendimento
     *
     * @param reportName        Nome do relatório
     * @param params            Parâmetros do relatório
     * @param reportInputStream Stream do Relatório (jxml)
     * @return Documento digital do relatório gerado
     */
    @Transactional
    DigitalDocumentDTO generateFromJxmlStream(ReportParam params) {
        try {
            return reportService.generateDocumentFromJxmlStream(new ClassPathResource(GENERIC_REPORT_SOURCE).getInputStream(),
                    "Relatório de Atendimento", params);
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar relatório.");
        }
    }

}
