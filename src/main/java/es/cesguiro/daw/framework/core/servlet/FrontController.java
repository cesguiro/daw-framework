package es.cesguiro.daw.framework.core.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.core.AppContext;
import es.cesguiro.daw.framework.core.exception.GlobalExceptionHandler;
import es.cesguiro.daw.framework.core.exception.ResourceNotFoundException;
import es.cesguiro.daw.framework.core.http.Request;
import es.cesguiro.daw.framework.core.http.Response;
import es.cesguiro.daw.framework.core.routing.RouteHandler;
import es.cesguiro.daw.framework.core.routing.Router;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

    private Router router;
    private ObjectMapper objectMapper;
    private GlobalExceptionHandler exceptionHandler;

    @Override
    public void init() throws ServletException {
        this.router = AppContext.getInstance().getBean(Router.class);
        this.objectMapper = new ObjectMapper();
        this.exceptionHandler = new GlobalExceptionHandler();
    }

    @Override
    protected void service(HttpServletRequest rawRequest, HttpServletResponse rawResponse) {
        try {
            Request request = new Request(rawRequest);

            RouteHandler routeHandler = router.resolve(request);
            if (routeHandler == null) {
                throw new ResourceNotFoundException("Path no encontrado: " + request.getPath());
            }
            Response response = routeHandler.handle(request);
            sendJsonResponse(rawResponse, response);
        } catch (Exception e) {
            exceptionHandler.handle(e, rawResponse);
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
