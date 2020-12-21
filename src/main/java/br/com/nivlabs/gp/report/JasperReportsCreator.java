package br.com.nivlabs.gp.report;

import java.io.InputStream;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * 
 * Gerador de relatórios Jasper
 * 
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 21 de jun de 2020
 *
 *
 */
public class JasperReportsCreator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSource datasource;

    /**
     * Cria o Jasper Print para manipulação
     * 
     * @param paramsToReport - Parâmetros do relatório
     * @param reportInputStream - InputStream do relatório (JXML)
     * @return instância do JasperPrint com buffer do relatório construído para a manipulação
     * @throws JRException
     */
    public JasperPrint create(ReportParam paramsToReport, InputStream reportInputStream) throws JRException {
        return getPrinterByStream(paramsToReport, reportInputStream);
    }

    /**
     * Cria o JasperPrint com os parâmetros
     * 
     * @param paramsToReport - Parâmetros do relatório
     * @param reportInputStream - InputStream do relatório (JXML)
     * @return instância do JasperPrint com buffer do relatório construído para a manipulação
     * @throws JRException
     */
    private JasperPrint getPrinterByStream(ReportParam paramsToReport, InputStream reportStream)
            throws JRException {
        JasperReport reportCompiled = null;
        try {
            reportCompiled = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams(), datasource.getConnection());
        } catch (Exception e) {
            logger.error("Falha ao tentar compilar o relatório para o Jasper", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na criação do relatório");
        }
    }

}
