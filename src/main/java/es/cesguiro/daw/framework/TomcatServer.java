package es.cesguiro.daw.framework;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TomcatServer {

    private final Logger logger = LoggerFactory.getLogger(TomcatServer.class);
    private final Tomcat tomcat;

    public TomcatServer() {
        this.tomcat = new Tomcat();

        this.tomcat.setPort(PropertyUtil.getInt("server.port", 8080));
        this.tomcat.getConnector();

        String docBase = new File(".").getAbsolutePath();

        String contextPath = PropertyUtil.get("server.context-path", "");

        if ("/".equals(contextPath)) {
            contextPath = "";
        }

        Context context = this.tomcat.addContext(contextPath, docBase);

        Tomcat.addServlet(context, "FrontController", new FrontController());
        context.addServletMapping("/*", "FrontController");

    }

    public void start(){
        try {
            logger.info("Arrancando el servidor Tomcat...");
            this.tomcat.start();
            this.tomcat.getServer().await(); //Mantiene la JVM escuchando peticiones
        } catch (Exception e) {
            logger.error("Error al arrancar el servidor Tomcat", e);
        }
    }
}
