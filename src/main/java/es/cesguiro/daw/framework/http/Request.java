package es.cesguiro.daw.framework.http;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String[]> queryParams;
    private final HttpServletRequest rawRequest;

    public Request(HttpServletRequest rawRequest) {
        this.rawRequest = rawRequest;
        this.method = rawRequest.getMethod().toUpperCase();

        // Limpiar el ContextPath de Tomcat para obtener la ruta relativa pura
        // elimina el prefijo del contexto definido en server.context-path de application.properties
        String uri = rawRequest.getRequestURI();
        String contextPath = rawRequest.getContextPath();
        this.path = (contextPath != null && !contextPath.isEmpty())
                ? uri.substring(contextPath.length())
                : uri;

        this.headers = extractHeaders(rawRequest);
        this.queryParams = rawRequest.getParameterMap();
    }

    private Map<String, String> extractHeaders(HttpServletRequest rawRequest) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headersName = rawRequest.getHeaderNames();

        if (headersName != null) {
            headersName.asIterator().forEachRemaining(
                    headerName -> map.put(headerName.toLowerCase(), rawRequest.getHeader(headerName))
            );
        }

        return Collections.unmodifiableMap(map);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String name) {
        return (name != null)
                ? headers.get(name.toLowerCase())
                : null;
    }

    public Map<String, String[]> getQueryParams() {
        return queryParams;
    }

    public String getQueryParam(String name) {
        String[] values = queryParams.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    public HttpServletRequest getRawRequest() {
        return rawRequest;
    }

}
