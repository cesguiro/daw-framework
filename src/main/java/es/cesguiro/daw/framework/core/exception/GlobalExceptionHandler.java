package es.cesguiro.daw.framework.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void handle(Throwable throwable, HttpServletResponse response) {
        int status;
        String message;

        if (throwable instanceof CustomException customException) {
            status = customException.getHttpStatus();
            message = customException.getMessage();
            logger.warn("Excepción controlada (HTTP {}): {}", status, message);
        } else {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            message = "Error interno del servidor";
            logger.error("Excepción no controlada (HTTP 500): ", throwable);
        }

        sendErrorResponse(response, status, message);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", status);
        errorBody.put("error", message);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorBody));
        } catch (IOException e) {
            logger.error("Error escribiendo la respuesta de error JSON: {}", e.getMessage(), e);
        }
    }
}
