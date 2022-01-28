package br.com.nivlabs.gp.config.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;

/**
 * Controlador Multi Tenant
 * 
 * @author viniciosarodrigues
 * @since 08/01/2022
 *
 */
@Component
public class MultiTenantConnectionProviderCustom implements MultiTenantConnectionProvider {

    @Autowired
    private Logger logger;

    private static final long serialVersionUID = 6599108049945296684L;

    private final DataSource dataSource;

    public MultiTenantConnectionProviderCustom(DataSource dataSource) {
        this.dataSource = dataSource;
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
        final Connection connection = getAnyConnection();
        try {
            connection.createStatement().execute("USE " + tenantIdentifier);
        } catch (SQLException e) {
            logger.error("Não foi possivel alterar para o schema {}", tenantIdentifier, e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao iniciar conexão com banco, verifique os logs para mais informações!");
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try (connection) {
            connection.createStatement().execute("USE " + TenantContext.DEFAULT_TENANT);
        } catch (SQLException e) {
            logger.error("Não foi se conectar ao schema padrão", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi se conectar ao schema padrão");
        }
    }

    @Override
    public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}
