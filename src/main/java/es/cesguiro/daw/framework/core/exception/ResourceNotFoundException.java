package es.cesguiro.daw.framework.core.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message) {
        super(message, HttpServletResponse.SC_NOT_FOUND);
    }
}
