package br.com.tl.gdp.report;

import java.io.InputStream;

import br.com.tl.gdp.exception.GenerateReportException;
import br.com.tl.gdp.exception.ValidationException;
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
                throw new ValidationException("Tipo de relatório inexistente");
        }

    }

    /**
     * Lança exceção base para tipos de relatórios divergentes
     * 
     * @param type
     */
    private void throwParamsDoNotMatch(ReportType type) {
        throw new GenerateReportException(
                String.format("Os parâmetros enviados não pertencem ao relatório solicitado :: %s :: %s",
                              type, type.getDescription()));

    }

    /**
     * Trata o Jasper Print com os parâmetros
     * 
     * @param paramsToReport
     * @param reportStream
     * @return
     * @throws JRException
     */
    private JasperPrint getPrinterByStream(AttendanceStatement paramsToReport, InputStream reportStream) throws JRException {
        JasperReport reportCompiled = JasperCompileManager.compileReport(reportStream);
        JasperPrint printer = JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams());
        return printer;
    }

    /**
     * Gera o Jasper Print para a Declaração de Comparecimento
     * 
     * @param params
     * @return
     * @throws JRException
     */
    private JasperPrint generateAttendanceStatement(ReportParam params) throws JRException {
        if (!(params instanceof AttendanceStatement))
            throwParamsDoNotMatch(ReportType.AttendanceStatement);
        return getPrinterByStream((AttendanceStatement) params, getClass().getResourceAsStream(_ReportPathFiles.AttendanceStatementFile));
    }

    /**
     * Interface com as informações de todos os relatórios da aplicação
     * 
     * @author viniciosarodrigues
     *
     */
    interface _ReportPathFiles {
        public static final String AttendanceStatementFile = "/reports/dec_omparecimento.jrxml";
    }
}
