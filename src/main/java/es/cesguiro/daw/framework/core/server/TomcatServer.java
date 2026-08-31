package es.cesguiro.daw.framework.core.server;

import es.cesguiro.daw.framework.core.util.PropertyUtil;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomcatServer {

    private final Tomcat tomcat;
    private final Logger logger = LoggerFactory.getLogger(TomcatServer.class);
    private final int serverPort = PropertyUtil.getInt("server.port", 8080);

    public TomcatServer() {
        tomcat = new Tomcat();
        tomcat.setPort(serverPort);
        tomcat.getConnector();
    }

    public void start(){
        try {
            logger.info("Arrancando el servidor Tomcat en el puerto " + serverPort + "...");
            tomcat.start();
            logger.info("Servidor Tomcat arrancado correctamente.");
            tomcat.getServer().await();
        } catch (Exception e) {
            logger.error("Error al arrancar el servidor Tomcat: " + e.getMessage());
        }
    }
}
