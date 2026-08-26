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
        //configurar puerto
        this.tomcat.setPort(8080);
        // inicializar el conector Coyote (HTTP) en el puerto configurado
        // El método getConnector() comprueba si ya existe un conector asignado.
        // Si no existe, instancia automáticamente el conector HTTP predeterminado de Coyote,
        // le asigna el puerto configurado y lo añade al servidor
        this.tomcat.getConnector();

        // Equivalente:
        // Connector connector = new Connector();
        // connector.setPort(8080)
        // this.tomcat.setConnector(connector)

        // Crear el contexto raiz de Catalina
        String docBase = new File(".").getAbsolutePath();
        // El primer parámetro es la URL base que tendrá la aplicación en el navegador
        // El segundo parámetro es la ubicación física en el disco duro donde se guardan los archivos webs estáticos
        Context context = this.tomcat.addContext("", docBase);
        //Context context = this.tomcat.addContext("/api/v1", docBase);

        //Registrar el servlet FrontController
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
