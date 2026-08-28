package es.cesguiro.daw.framework.servlet;

import es.cesguiro.daw.framework.context.AppContext;
import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.http.Request;
import es.cesguiro.daw.framework.http.Response;
import es.cesguiro.daw.framework.routing.RouteHandler;
import es.cesguiro.daw.framework.routing.Router;
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
    private Router router;


    @Override
    public void init(){
        router = AppContext.getInstance().getBean(Router.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request request = new Request(req);

        // 1. Resolver y ejecutar la ruta
        Response response = processRequest(request);

        // 2. Enviar la respuesta a Tomcat
        sendResponse(response, resp);
    }

    /**
     * Busca la ruta registrada y ejecuta su handler.
     * Si no existe devuelve 404, y si ocurre un fallo devuelve 500.
     */
    private Response processRequest(Request request) {
        RouteHandler handler = router.resolve(request);

        if (handler == null) {
            return Response.notFound();
        }

        try {
            return handler.handle(request);
        } catch (Exception e) {
            logger.error("Error procesando petición", e);
            return new Response(500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    private Router getRouter() {
        if (router == null) {
            router = AppContext.getInstance().getBean(Router.class);
        }
        return router;
    }

    /**
     * Vuelca la información de nuestro objeto Response al HttpServletResponse de Jakarta.
     */
    private void sendResponse(Response response, HttpServletResponse resp) throws IOException {
        // 1. Código de estado HTTP
        resp.setStatus(response.getStatus());

        // 2. Cabeceras HTTP
        response.getHeaders().forEach(resp::setHeader);

        // 3. Cuerpo de la respuesta
        if (response.getBody() != null) {
            writeResponseBody(response.getBody(), resp);
        }
    }

    /**
     * Escribe el body asignando un Content-Type por defecto si no se definió uno.
     */
    private void writeResponseBody(Object body, HttpServletResponse resp) throws IOException {
        if (resp.getContentType() == null) {
            resp.setContentType("application/json; charset=UTF-8");
        }
        resp.getWriter().write(body.toString());
    }
}
