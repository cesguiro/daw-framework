package es.cesguiro.daw.framework.routing;

import java.util.Objects;

public class RouteKey {

    private final String method;
    private final String path;

    public RouteKey(String method, String path) {
        this.method = method.toUpperCase();
        this.path = sanitizePath(path);
    }

    private String sanitizePath(String path) {
        if (path == null || path.isEmpty()) return "/";
        String clean = path.startsWith("/") ? path : "/" + path;
        return (clean.length() > 1 && clean.endsWith("/"))
                ? clean.substring(0, clean.length() - 1)
                : clean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteKey routeKey = (RouteKey) o;
        return Objects.equals(method, routeKey.method) && Objects.equals(path, routeKey.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}