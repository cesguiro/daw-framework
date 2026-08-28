package es.cesguiro.daw.framework.listener;

import es.cesguiro.daw.framework.config.AppRoutes;
import es.cesguiro.daw.framework.context.AppContext;
import es.cesguiro.daw.framework.routing.Router;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class AppServletContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DataSource dataSource = DataSourceManager.initialize();
            DatabaseMigrator.migrate(dataSource);
            AppContext.getInstance().register(DataSource.class, dataSource);

            Router router = AppRoutes.register();
            AppContext.getInstance().register(Router.class, router);

            logger.info("Contexto de la aplicación inicializado correctamente");
        } catch (Exception e) {
            logger.error("Error al inicializar el contexto de la aplicación", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSourceManager.close();
    }
}