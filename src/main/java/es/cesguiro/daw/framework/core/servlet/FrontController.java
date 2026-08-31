package es.cesguiro.daw.framework.core.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FrontController extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        logger.info("FrontController service called");
        logger.info("Request Method: " + httpServletRequest.getMethod());
        logger.info("Request URI: " + httpServletRequest.getRequestURI());
        logger.info("Response Status: " + httpServletResponse.getStatus());
        sendResponse(httpServletResponse);
    }

    private void sendResponse(HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain;charset=UTF-8");
        try {
            response.getWriter().write("Hello from FrontController!");
        } catch (IOException e) {
            logger.error("Error writing response: {}", e.getMessage(), e);
        }
    }
}
