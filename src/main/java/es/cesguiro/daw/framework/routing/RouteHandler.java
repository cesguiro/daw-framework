package es.cesguiro.daw.framework.routing;

import es.cesguiro.daw.framework.http.Request;
import es.cesguiro.daw.framework.http.Response;

@FunctionalInterface
public interface RouteHandler {

    Response handle(Request request) throws Exception;

}
