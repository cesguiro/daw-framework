package es.cesguiro.daw.framework.core.servlet;

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

    @Override
    public void init() throws ServletException {
        userController = new UserController();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        logger.info("Procesando petición: {} {}", request.getMethod(), requestURI);

        if ("/api/users".equals(requestURI)) {
            sendResponse(response, HttpServletResponse.SC_OK, userController.findAll().toString());
        } else if ("/api/users/detail".equals(requestURI)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    long id = Long.parseLong(idParam);
                    sendResponse(response, HttpServletResponse.SC_OK, userController.findById(id).toString());
                } catch (NumberFormatException e) {
                    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format: " + idParam);
                }
            } else {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing required parameter 'id'");
            }
        } else {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, "Path not found: " + requestURI);
        }
    }

    private void sendResponse(HttpServletResponse response, int status, String responseBody) {
        response.setStatus(status);
        response.setContentType("text/plain;charset=UTF-8");
        try {
            response.getWriter().write(responseBody);
        } catch (IOException e) {
            logger.error("Error writing response: {}", e.getMessage(), e);
        }
    }

}
