package es.cesguiro.daw.framework.core.routing;

import es.cesguiro.daw.framework.core.http.Request;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private final Map<RouteKey, RouteHandler> routes = new HashMap<>();

    public Router get(String path, RouteHandler handler) {
        routes.put(new RouteKey("GET", path), handler);
        return this;
    }

    public Router post(String path, RouteHandler handler) {
        routes.put(new RouteKey("POST", path), handler);
        return this;
    }

    public Router put(String path, RouteHandler handler) {
        routes.put(new RouteKey("PUT", path), handler);
        return this;
    }

    public Router delete(String path, RouteHandler handler) {
        routes.put(new RouteKey("DELETE", path), handler);
        return this;
    }

    public RouteHandler resolve(Request request) {
        return routes.get(new RouteKey(request.getMethod(), request.getPath()));
    }
}
