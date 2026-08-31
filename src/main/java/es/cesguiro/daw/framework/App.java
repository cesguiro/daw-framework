package es.cesguiro.daw.framework;

import es.cesguiro.daw.framework.core.server.TomcatServer;

public class App {

    static void main() {
        try {
            TomcatServer tomcatServer = new TomcatServer();
            tomcatServer.start();
        } catch (Exception e) {
            IO.println("Error en la aplicación: " + e.getMessage());
        }
    }
}
