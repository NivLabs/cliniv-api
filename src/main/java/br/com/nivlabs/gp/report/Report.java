package br.com.nivlabs.gp.report;

import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * 
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 21 de jun de 2020
 *
 * @param <P> - Parâmetros do relatório
 *
 */
public class Report {

    /**
     * Gera o Jasper Print
     * 
     * @param params
     * @param reportInputStream
     * @return
     * @throws JRException
     */
    public JasperPrint getJasperPrint(ReportParam params, InputStream reportInputStream) throws JRException {
        return getPrinterByStream(params, reportInputStream);
    }

    /**
     * Trata o Jasper Print com os parâmetros
     * 
     * @param paramsToReport
     * @param reportStream
     * @return
     * @throws JRException
     */
    private JasperPrint getPrinterByStream(ReportParam paramsToReport, InputStream reportStream)
            throws JRException {
        JasperReport reportCompiled = JasperCompileManager.compileReport(reportStream);
        return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams());
    }
}
