package br.com.nivlabs.cliniv.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;

import br.com.nivlabs.cliniv.config.db.TenantContext;
import br.com.nivlabs.cliniv.exception.HttpException;
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
     * Contrói dinamicamente o datasource para o Jasper
     * 
     * @param tenantIdentifier Identificador do Schema
     * @return Conexão válida
     * @throws SQLException
     */
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = DataSourceUtils.doGetConnection(datasource);
        try {
            connection.createStatement().execute("USE " + tenantIdentifier);
        } catch (SQLException e) {
            logger.error("Não foi possivel alterar para o schema {}", tenantIdentifier, e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao iniciar conexão com banco, verifique os logs para mais informações!");
        }
        return connection;
    }

    /**
     * Cria o JasperPrint com os parâmetros
     * 
     * @param paramsToReport - Parâmetros do relatório
     * @param reportInputStream - InputStream do relatório (JXML)
     * @return instância do JasperPrint com buffer do relatório construído para a manipulação
     * @throws JRException
     */
    @Transactional
    private JasperPrint getPrinterByStream(ReportParam paramsToReport, InputStream reportStream)
            throws JRException {
        JasperReport reportCompiled = null;
        Connection connection = null;
        try {
            connection = getConnection(TenantContext.getCurrentTenant());
            logger.info("Iniciando processo de geração de documento :: Datasource Utilizado: {}", TenantContext.getCurrentTenant());
            reportCompiled = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams(), connection);
        } catch (Exception e) {
            logger.error("Falha ao tentar compilar o relatório para o Jasper", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na criação do relatório");
        } finally {
            try {
                if (datasource.getConnection() != null && !datasource.getConnection().isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.warn("Não foi possível encerrar a conexão :: ", e);
            }
        }
    }

}
