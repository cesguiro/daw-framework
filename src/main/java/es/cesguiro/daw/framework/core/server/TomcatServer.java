package es.cesguiro.daw.framework.core.server;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomcatServer {

    private final Tomcat tomcat;
    private final Logger logger = LoggerFactory.getLogger(TomcatServer.class);

    public TomcatServer() {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
    }

    public void start(){
        try {
            logger.info("Arrancando el servidor Tomcat en el puerto 8080...");
            tomcat.start();
            logger.info("Servidor Tomcat arrancado correctamente.");
            tomcat.getServer().await();
        } catch (Exception e) {
            logger.error("Error al arrancar el servidor Tomcat: " + e.getMessage());
        }
    }
}
