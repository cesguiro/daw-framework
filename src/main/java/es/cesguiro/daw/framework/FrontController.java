package es.cesguiro.daw.framework;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FrontController extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Petición GET recibida en FrontController: {}", request.getRequestURI());
        response.setContentType("text/html");
        response.getWriter().write("<html><body><h1>Hola desde el FrontController</h1></body></html>");
    }
}
