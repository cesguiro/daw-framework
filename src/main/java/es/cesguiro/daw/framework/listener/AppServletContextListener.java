package es.cesguiro.daw.framework.listener;

import es.cesguiro.daw.framework.context.AppContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class AppServletContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource dataSource = DataSourceManager.initialize();
        DatabaseMigrator.migrate(dataSource);
        AppContext.getInstance().register(DataSource.class, dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSourceManager.close();
    }
}