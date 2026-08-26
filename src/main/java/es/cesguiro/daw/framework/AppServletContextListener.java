package es.cesguiro.daw.framework;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppServletContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);
    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("==================================================");
        logger.info("  Inicializando infraestructura de Base de Datos  ");
        logger.info("==================================================");

        try {
            // 1. Instanciar HikariCP con PropertyUtil
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(PropertyUtil.get("db.url"));
            config.setUsername(PropertyUtil.get("db.username"));
            config.setPassword(PropertyUtil.get("db.password"));
            config.setDriverClassName(PropertyUtil.get("db.driver"));

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);

            this.dataSource = new HikariDataSource(config);
            logger.info("Pool de conexiones HikariCP configurado correctamente.");

            // 2. Comprobar flyway.enabled
            boolean flywayEnabled = PropertyUtil.getBoolean("flyway.enabled", true);

            if (flywayEnabled) {
                logger.info("Ejecutando migraciones de Flyway en MariaDB...");
                Flyway flyway = Flyway.configure()
                        .dataSource(this.dataSource)
                        .locations(PropertyUtil.get("flyway.locations", "classpath:db/migration"))
                        .load();

                int migrations = flyway.migrate().migrationsExecuted;
                logger.info("Flyway finalizado: {} migraciones aplicadas.", migrations);
            } else {
                logger.info("Migraciones de Flyway deshabilitadas (flyway.enabled=false).");
            }

            // 3. Registrar el DataSource globalmente en el ServletContext
            sce.getServletContext().setAttribute("dataSource", this.dataSource);

        } catch (Exception e) {
            logger.error("Error crítico al inicializar la base de datos", e);
            throw new RuntimeException("Error en inicio de infraestructura BD", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("==================================================");
        logger.info("  Cerrando pool de conexiones de Base de Datos    ");
        logger.info("==================================================");

        if (this.dataSource != null && !this.dataSource.isClosed()) {
            this.dataSource.close();
            logger.info("HikariDataSource cerrado con éxito.");
        }
    }
}