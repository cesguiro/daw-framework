package es.cesguiro.daw.framework.core.server;

import es.cesguiro.daw.framework.core.servlet.FrontController;
import es.cesguiro.daw.framework.core.util.PropertyUtil;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TomcatServer {

    private final Tomcat tomcat;
    private final Logger logger = LoggerFactory.getLogger(TomcatServer.class);
    private final int serverPort = PropertyUtil.getInt("app.server.port", 8080);

    public TomcatServer() {
        tomcat = new Tomcat();
        tomcat.setPort(serverPort);
        tomcat.getConnector();

        String docBase = new File(".").getAbsolutePath();
        String contextPath = PropertyUtil.get("app.server.context-path", "/");
        if("/".equals(contextPath)) {
            contextPath = "";
        }
        Context context = tomcat.addContext(contextPath, docBase);

        configureClasspath(context);
        Tomcat.addServlet(context, "FrontController", FrontController.class.getName());
        context.addServletMapping("/*", "FrontController");
    }

    public void start(){
        try {
            logger.info("Arrancando el servidor Tomcat en el puerto " + serverPort + "...");
            tomcat.start();
            logger.info("Servidor Tomcat arrancado correctamente.");
            tomcat.getServer().await();
        } catch (Exception e) {
            logger.error("Error al arrancar el servidor Tomcat: {}", e.getMessage(), e);
        }
    }

    /**
     * Configura el ClassLoader y los recursos para que Tomcat Embebido
     * encuentre los .class compilados y las dependencias del proyecto.
     */
    private void configureClasspath(Context context) {
        context.setParentClassLoader(Thread.currentThread().getContextClassLoader());

        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        context.setResources(resources);
    }

}
