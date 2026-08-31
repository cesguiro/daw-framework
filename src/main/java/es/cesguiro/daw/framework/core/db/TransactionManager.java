package es.cesguiro.daw.framework.core.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    // Almacena una Connection exclusiva por cada hilo de ejecución (request HTTP)
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private static DataSource dataSource;

    public static void init(DataSource dataSource) {
        TransactionManager.dataSource = dataSource;
    }

    /**
     * Inicia la transacción desactivando el auto-commit en la conexión del hilo actual.
     */
    public static void beginTransaction() {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connectionHolder.set(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error al iniciar la transacción", e);
        }
    }

    /**
     * Devuelve la conexión activa de la transacción actual.
     * Si no hay transacción iniciada, solicita una conexión estándar al pool.
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null && !connection.isClosed()) {
            return connection; // Retorna la conexión transaccional compartida
        }
        return dataSource.getConnection(); // Retorna conexión normal del pool
    }

    /**
     * Confirma los cambios realizados en la transacción y libera la conexión.
     */
    public static void commit() {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException("Error durante el commit de la transacción", e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    /**
     * Revierte todos los cambios realizados en la transacción y libera la conexión.
     */
    public static void rollback() {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Error durante el rollback de la transacción", e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.setAutoCommit(true); // Restauramos el estado por defecto
            connection.close(); // Devuelve la conexión al pool de HikariCP
        } catch (SQLException e) {
            // Log de advertencia si falla al cerrar
        } finally {
            connectionHolder.remove(); // ¡CRÍTICO! Limpia el ThreadLocal para evitar fugas de memoria
        }
    }
}
