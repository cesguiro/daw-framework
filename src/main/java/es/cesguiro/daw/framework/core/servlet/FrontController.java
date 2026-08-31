package es.cesguiro.daw.framework.core.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.core.exception.GlobalExceptionHandler;
import es.cesguiro.daw.framework.core.exception.ResourceNotFoundException;
import es.cesguiro.daw.framework.core.http.Request;
import es.cesguiro.daw.framework.core.http.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    protected void service(HttpServletRequest rawRequest, HttpServletResponse rawResponse) {
        try {
            Request request = new Request(rawRequest);
            Response response = dispatch(request);
            sendJsonResponse(rawResponse, response);
        } catch (Exception e) {
            exceptionHandler.handle(e, rawResponse);
        }
    }

    private Response dispatch(Request request) throws Exception {
        String requestURI = request.getPath();

        if ("/users".equals(requestURI)) {
            return userController.findAll();
        } else if ("/users/detail".equals(requestURI)) {
            String idParam = request.getQueryParam("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("El parámetro 'id' es obligatorio");
            }
            long id = Long.parseLong(idParam);
            return userController.findById(id);
        } else {
            throw new ResourceNotFoundException("Path no encontrado: " + requestURI);
        }
    }

    private void sendJsonResponse(HttpServletResponse rawResponse, Response response) throws Exception {
        rawResponse.setStatus(response.getStatus());
        rawResponse.setContentType("application/json;charset=UTF-8");
        response.getHeaders().forEach(rawResponse::addHeader);

        if(response.getBody() != null) {
            String jsonResponseBody = objectMapper.writeValueAsString(response.getBody());
            rawResponse.getWriter().write(jsonResponseBody);
        }
    }
}
