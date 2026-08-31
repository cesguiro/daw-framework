package es.cesguiro.daw.framework.core.routing;

import es.cesguiro.daw.framework.core.http.Request;
import es.cesguiro.daw.framework.core.http.Response;

@FunctionalInterface
public interface RouteHandler {

    Response handle(Request request) throws Exception;
}
