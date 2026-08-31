package es.cesguiro.daw.framework.core.config;

import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.core.AppContext;
import es.cesguiro.daw.framework.core.routing.Router;
import es.cesguiro.daw.framework.routes.ApiRoutes;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import javax.sql.DataSource;

/**
 * Escuchador del ciclo de vida de la aplicación Servlet.
 * <p>
 * Los métodos de esta clase se ejecutan en el arranque del servidor, ANTES de que
 * cualquier Servlet (como el FrontController) sea inicializado o atienda peticiones.
 * Es el lugar ideal para configurar la Inversión de Control (IoC) e instanciar
 * los componentes principales (Beans) en el AppContext.
 */
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AppContext appContext = AppContext.getInstance();
        DataSource dataSource = DataSourceManager.initialize();
        appContext.register(DataSource.class, dataSource);

        DatabaseMigrator.migrate(dataSource);

        appContext.register(UserController.class, new UserController());
        Router router = ApiRoutes.configure();
        appContext.register(Router.class, router);
    }
}
