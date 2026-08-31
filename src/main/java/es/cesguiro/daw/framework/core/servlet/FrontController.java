package es.cesguiro.daw.framework.core.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cesguiro.daw.framework.controller.UserController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FrontController extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(FrontController.class);
    private UserController userController;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.userController = new UserController();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        logger.info("Procesando petición HTTP: {} {}", request.getMethod(), requestURI);

        if ("/api/users".equals(requestURI)) {
            String jsonResponseBody = objectMapper.writeValueAsString(userController.findAll());
            sendJsonResponse(response, HttpServletResponse.SC_OK, jsonResponseBody);

        } else if ("/api/users/detail".equals(requestURI)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    long id = Long.parseLong(idParam);
                    Object user = userController.findById(id);
                    if (user != null) {
                        String jsonResponseBody = objectMapper.writeValueAsString(user);
                        sendJsonResponse(response, HttpServletResponse.SC_OK, jsonResponseBody);
                    } else {
                        sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND,
                                "{\"error\": \"Usuario no encontrado\"}");
                    }
                } catch (NumberFormatException e) {
                    sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                            "{\"error\": \"El formato del parámetro 'id' debe ser numérico\"}");
                }
            } else {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                        "{\"error\": \"El parámetro 'id' es obligatorio\"}");
            }
        } else {
            sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    "{\"error\": \"Path no encontrado: " + requestURI + "\"}");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, int status, String jsonResponseBody) {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(jsonResponseBody);
        } catch (IOException e) {
            logger.error("Error al escribir la respuesta JSON: {}", e.getMessage(), e);
        }
    }
}
