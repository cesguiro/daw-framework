package es.cesguiro.daw.framework.core.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import es.cesguiro.daw.framework.core.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DataSourceManager {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
    private static HikariDataSource dataSource;

    public static DataSource initialize(){
        logger.info("  Inicializando pool de conexiones HikariCP...  ");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertyUtil.get("db.url"));
        config.setUsername(PropertyUtil.get("db.username"));
        config.setPassword(PropertyUtil.get("db.password"));
        config.setDriverClassName(PropertyUtil.get("db.driver"));

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        config.setConnectionTimeout(30000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(180000);

        dataSource = new HikariDataSource(config);
        logger.info("Pool de conexiones HikariCP configurado correctamente.");
        return dataSource;
    }

    public static void close(){
        if (dataSource != null && !dataSource.isClosed()){
            logger.info("Cerrando pool de conexiones HikariCP...  ");
            dataSource.close();
        }
    }
}
