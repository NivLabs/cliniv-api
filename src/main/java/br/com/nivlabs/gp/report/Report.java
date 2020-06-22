package br.com.nivlabs.gp.report;

import java.io.InputStream;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Fábria de relatórios
 * 
 * @author viniciosarodrigues
 *
 */
public class ReportFactory {

    /**
     * Construtor privado
     */
    private ReportFactory() {
    }

    /**
     * Busca instância única na aplicação
     * 
     * @return
     */
    public static ReportFactory getInstance() {
        return new ReportFactory();
    }

    /**
     * Busca o Jasper Print certo para o relatório específico
     * 
     * @param params
     * @param type
     * @return
     * @throws JRException
     */
    public JasperPrint getJasperPrint(ReportParam params, ReportType type) throws JRException {
        switch (type) {
            case AttendanceStatement:
                return generateAttendanceStatement(params);
            default:
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de relatório inexistente");
        }

    }

    /**
     * Lança exceção base para tipos de relatórios divergentes
     * 
     * @param type
     */
    private void throwParamsDoNotMatch(ReportType type) {
        throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                String.format("Os parâmetros enviados não pertencem ao relatório solicitado :: %s :: %s", type,
                              type.getDescription()));

    }

    /**
     * Trata o Jasper Print com os parâmetros
     * 
     * @param paramsToReport
     * @param reportStream
     * @return
     * @throws JRException
     */
    private JasperPrint getPrinterByStream(AttendanceParams paramsToReport, InputStream reportStream)
            throws JRException {
        JasperReport reportCompiled = JasperCompileManager.compileReport(reportStream);
        return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams());
    }

    /**
     * Gera o Jasper Print para a Declaração de Comparecimento
     * 
     * @param params
     * @return
     * @throws JRException
     */
    private JasperPrint generateAttendanceStatement(ReportParam params) throws JRException {
        if (!(params instanceof AttendanceParams))
            throwParamsDoNotMatch(ReportType.AttendanceStatement);
        return getPrinterByStream((AttendanceParams) params,
                                  getClass().getResourceAsStream(_ReportPathFiles.AttendanceStatementReportFile));
    }

    /**
     * Interface com as informações de todos os relatórios da aplicação
     * 
     * @author viniciosarodrigues
     *
     */
    interface _ReportPathFiles {
        public static final String AttendanceStatementReportFile = "/reports/dec_comparecimento.jrxml";
        public static final String AnamnesisReportFile = "/reports/anamnesis_report.jrxml";
    }
}
