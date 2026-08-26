package es.cesguiro.daw.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    static void main() {
        // 1. Desvia los logs de Tomcat (JULI) hacia SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        TomcatServer tomcatServer = new TomcatServer();
        tomcatServer.start();
    }
}
