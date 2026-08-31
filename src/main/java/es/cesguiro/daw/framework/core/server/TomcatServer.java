package es.cesguiro.daw.framework.core.server;

import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

    private final Tomcat tomcat;

    public TomcatServer() {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
    }

    public void start(){
        try {
            IO.println("Arrancando el servidor Tomcat en el puerto 8080...");
            tomcat.start();
            IO.println("Servidor Tomcat arrancado correctamente.");
            tomcat.getServer().await();
        } catch (Exception e) {
            IO.println("Error al arrancar el servidor Tomcat: " + e.getMessage());
        }
    }
}
