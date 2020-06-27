package br.com.nivlabs.gp.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Classe que realiza a criação de datasource para geração de relatórios com Jasper
 * 
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 24 de jun de 2020
 *
 *
 */
@Configuration
public class ReportConnectionConfiguration {

    private static Logger logger = LoggerFactory.getLogger(ReportConnectionConfiguration.class);

    private static final String DRIVER_NAME = "org.mariadb.jdbc.Driver";

    private Connection connectionInstance;

    @Profile("prod")
    @Bean
    public String getHerokuDatasource() {
        return "Datasource Report Connection for production deploy";
    }

    @Profile("dev")
    @Bean
    public String getDevDatasource() {
        return "Datasource Report for Development deploy";
    }

    @Value("${report.connection-string}")
    private String stringConnection;

    /**
     * Cria a conexão para o Jasper
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        if (connectionInstance == null) {
            logger.info("Ainda não existe uma conexão, estamos criando uma...");
            connectionInstance = DriverManager.getConnection(stringConnection);
            logger.info("Conexão criada com sucesso!");
        }
        return connectionInstance;
    }

    protected void closeConnection() throws SQLException {
        logger.info("Fechando conexão");
        if (connectionInstance != null && !connectionInstance.isClosed())
            connectionInstance.close();
        connectionInstance = null;
    }

    @Bean
    public Report reportProvider() {
        return new Report(this);
    }

}
