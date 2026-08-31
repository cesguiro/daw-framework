package es.cesguiro.daw.framework.core.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private final int status;
    private final Object body;
    private final Map<String, String> headers;


    public Response(int status, Object body) {
        this(status, body, new HashMap<>());
    }

    public Response(int status, Object body, Map<String, String> headers) {
        this.status = status;
        this.body = body;
        this.headers = (headers != null) ? new HashMap<>(headers) : new HashMap<>();
    }

    // --- Métodos de factoría estáticos ---

    public static Response ok(Object body) {
        return new Response(200, body);
    }

    public static Response created(Object body) {
        return new Response(201, body);
    }

    public static Response badRequest(Object body) {
        return new Response(400, body);
    }

    public static Response unauthorized(Object body) {
        return new Response(401, body);
    }

    public static Response notFound() {
        return new Response(404, "404 Not Found");
    }

    public static Response status(int status) {
        return new Response(status, null);
    }

    // Permite añadir cabeceras de respuesta si fuera necesario
    public Response header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
}