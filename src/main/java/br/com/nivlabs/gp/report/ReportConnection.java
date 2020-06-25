package br.com.nivlabs.gp.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Classe que realiza a criação de datasource para geração de relatórios com Jasper
 * 
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 24 de jun de 2020
 *
 *
 */
@Configuration
public class ReportConnection {

    private static Logger logger = LoggerFactory.getLogger(ReportConnection.class);

    private static final String DRIVER_NAME = "org.mariadb.jdbc.Driver";

    @Value("report.connection-string")
    private String stringConnection;

    private Connection connectionInstance;

    /**
     * Cria a conexão para o Jasper
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        if (connectionInstance == null) {
            logger.info("Ainda não existe uma conexão, estamos criando uma...");
            connectionInstance = DriverManager.getConnection(stringConnection);
            logger.info("Conexão criada com sucesso!");
        }
        return connectionInstance;
    }

    public void closeConnection() throws SQLException {
        logger.info("Fechando conexão");
        if (connectionInstance != null && !connectionInstance.isClosed())
            connectionInstance.close();
    }

}
