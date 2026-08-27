package es.cesguiro.daw.framework.listener;

import es.cesguiro.daw.framework.util.PropertyUtil;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DatabaseMigrator {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrator.class);

    public static void migrate(DataSource dataSource) {
        boolean flywayEnabled = PropertyUtil.getBoolean("flyway.enabled", true);
        if (!flywayEnabled) {
            logger.info("Migraciones de Flyway deshabilitadas (flyway.enabled=false).");
            return;
        }
        logger.info("Iniciando migración de base de datos...");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(PropertyUtil.get("flyway.locations", "classpath:db/migration"))
                .load();
        int migrations = flyway.migrate().migrationsExecuted;
        logger.info("Flyway finalizado: {} migraciones aplicadas.", migrations);
    }
}
