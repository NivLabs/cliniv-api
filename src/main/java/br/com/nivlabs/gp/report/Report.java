package br.com.nivlabs.gp.report;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ReportConnectionConfiguration connection;

    public Report(ReportConnectionConfiguration connection) {
        this.connection = connection;
    }

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
        JasperReport reportCompiled = null;
        try {
            reportCompiled = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams(), connection.getConnection());
        } catch (Exception e) {
            logger.error("Falha ao tentar criar um datasource para o Jasper", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na criação do relatório");
        } finally {
            try {
                connection.closeConnection();
                reportCompiled = null;
                reportStream = null;
            } catch (SQLException e) {
                logger.error("Falha ao encerrar conexão com Datasource de relatórios", e);
            }

        }
    }

}
