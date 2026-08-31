package es.cesguiro.daw.framework.core.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.core.exception.GlobalExceptionHandler;
import es.cesguiro.daw.framework.core.exception.ResourceNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FrontController extends HttpServlet {

    private UserController userController;
    private ObjectMapper objectMapper;
    private GlobalExceptionHandler exceptionHandler;

    @Override
    public void init() throws ServletException {
        this.userController = new UserController();
        this.objectMapper = new ObjectMapper();
        this.exceptionHandler = new GlobalExceptionHandler();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {

        try {
            dispatch(request, response);
        } catch (Exception e) {
            exceptionHandler.handle(e, response);
        }
    }

    private void dispatch(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String requestURI = httpServletRequest.getRequestURI();

        if ("/api/users".equals(requestURI)) {
            String jsonResponseBody = objectMapper.writeValueAsString(userController.findAll());
            sendJsonResponse(httpServletResponse,jsonResponseBody);

        } else if ("/api/users/detail".equals(requestURI)) {
            String idParam = httpServletRequest.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("El parámetro 'id' es obligatorio");
            }
            long id = Long.parseLong(idParam);
            Object user = userController.findById(id);
            if (user == null) {
                throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado");
            }
            String jsonResponseBody = objectMapper.writeValueAsString(user);
            sendJsonResponse(httpServletResponse, jsonResponseBody);
        } else {
            throw new ResourceNotFoundException("Path no encontrado: " + requestURI);
        }
    }

    private void sendJsonResponse(HttpServletResponse httpServletResponse, String jsonResponseBody) throws Exception {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(jsonResponseBody);
    }
}
