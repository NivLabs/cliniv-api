package br.com.nivlabs.cliniv.config.db;

import br.com.nivlabs.cliniv.exception.HttpException;
import io.micrometer.common.lang.NonNull;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Controlador Multi Tenant
 *
 * @author viniciosarodrigues
 * @since 08/01/2022
 */
@Component
public class MultiTenantConnectionProviderCustom implements MultiTenantConnectionProvider<String> {

    private final Logger logger;
    private final DataSource dataSource;

    @Autowired
    public MultiTenantConnectionProviderCustom(DataSource dataSource, Logger logger) {
        this.dataSource = dataSource;
        this.logger = logger;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        logger.info("Capturando conexão para o tenant '{}'", tenantIdentifier);

        if (!tenantIdentifier.matches("[a-zA-Z0-9_]+")) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Tenant inválido: " + tenantIdentifier);
        }

        final Connection connection = getAnyConnection();
        try {
            String currentDb = connection.getCatalog();
            if (!tenantIdentifier.equals(currentDb)) {
                logger.info("Mudando schema de '{}' para '{}'", currentDb, tenantIdentifier);
                connection.setCatalog(tenantIdentifier);
            }
        } catch (SQLException e) {
            logger.error("Não foi possível alterar para o schema {}", tenantIdentifier, e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao iniciar conexão com banco, verifique os logs para mais informações!");
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        logger.trace("Liberando conexão para o Tenant '{}'", tenantIdentifier);
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public boolean isUnwrappableAs(@NonNull Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(@NonNull Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}
