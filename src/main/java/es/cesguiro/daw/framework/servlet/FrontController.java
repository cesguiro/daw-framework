package es.cesguiro.daw.framework.servlet;

import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.http.Request;
import es.cesguiro.daw.framework.http.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class FrontController extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(FrontController.class);
    private UserController userController;

    @Override
    public void init() throws ServletException {
        this.userController = new UserController();
    }

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        Request request = new Request(httpServletRequest);

        /*logger.info("FrontController: Request - Method: {}, Path: {}", request.getMethod(), request.getPath());
        Map<String, String> headers = request.getHeaders();
        for (String headerName : headers.keySet()) {
            logger.info("FrontController: Request - Header: {} = {}", headerName, request.getHeader(headerName));
        }
        Map<String, String[]> queryParams = request.getQueryParams();
        for (String paramName : queryParams.keySet()) {
            logger.info("FrontController: Request - Query Param: {} = {}", paramName, request.getQueryParam(paramName));
        }*/

        Response response;


        String path = request.getPath();

        if("/api/users".equals(path)) {
            response = userController.findAll(request);
        } else if("/api/users/detail".equals(path)) {
            response = userController.findById(request);
        } else {
            response = Response.notFound();
        }

        httpServletResponse.setStatus(response.getStatus());
        response.getHeaders().forEach(httpServletResponse::setHeader);

        if (response.getBody() != null) {
            if (httpServletResponse.getContentType() == null) {
                httpServletResponse.setContentType("application/json");
            }
            httpServletResponse.getWriter().write(response.getBody().toString());
        }
    }
}
