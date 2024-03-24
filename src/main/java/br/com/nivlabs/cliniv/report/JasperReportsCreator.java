package br.com.nivlabs.cliniv.report;

import br.com.nivlabs.cliniv.config.db.TenantContext;
import br.com.nivlabs.cliniv.exception.HttpException;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Gerador de relatórios Jasper
 *
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 21 de jun de 2020
 */
public class JasperReportsCreator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSource datasource;

    @Transactional
    public JasperPrint create(boolean isActiveConnection, ReportParam paramsToReport, InputStream reportInputStream) throws JRException {
        return getPrinterByStream(isActiveConnection, paramsToReport, reportInputStream);
    }

    public Connection getConnection(final String tenantIdentifier) throws SQLException {
        final Connection connection = datasource.getConnection();
        try {
            connection.createStatement().execute("USE " + tenantIdentifier);
        } catch (SQLException e) {
            logger.error("Não foi possivel alterar para o schema {}", tenantIdentifier, e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao iniciar conexão com banco, verifique os logs para mais informações!");
        }
        return connection;
    }

    @Transactional
    JasperPrint getPrinterByStream(boolean isActiveConnection, ReportParam paramsToReport, InputStream reportStream)
            throws JRException {

        JasperReport reportCompiled = null;
        Connection connection = null;
        try {
            reportCompiled = JasperCompileManager.compileReport(reportStream);
            if (!isActiveConnection) {
                return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams(), new JREmptyDataSource());
            }
            connection = getConnection(TenantContext.getCurrentTenant());
            logger.info("Iniciando processo de geração de documento :: Datasource Utilizado: {}", TenantContext.getCurrentTenant());
            return JasperFillManager.fillReport(reportCompiled, paramsToReport.getParams(), connection);
        } catch (Exception e) {
            logger.error("Falha ao tentar compilar o relatório para o Jasper", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na criação do relatório");
        } finally {
            try {
                if (isActiveConnection && connection != null && datasource.getConnection() != null && !datasource.getConnection().isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.warn("Não foi possível encerrar a conexão :: ", e);
            }
        }
    }

}
