package com.example.blog.filter;

import com.example.blog.controllers.MainController;
import com.example.blog.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminRoleFilter implements Filter {
    static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestUri = httpRequest.getRequestURI();
        log.info(requestUri);
        if (!requestUri.startsWith("/admin")) {
            // skip filter logic for requests that don't match "/admin"
            chain.doFilter(request, response);
            return;
        }
        User user = (User) httpRequest.getSession().getAttribute("userDTO");
        if (user != null) {
            if (user.hasRole("ROLE_ADMIN")) {
                log.info("User has required role (ROLE_ADMIN)");
                chain.doFilter(request, response);
                return;
            }            else {
                log.info("User does not have required role (ROLE_ADMIN)");
            }
            if (user.hasRole("ROLE_USER")) {
                log.info("User has role (ROLE_USER)");
                log.info("redirect to /home");
                httpResponse.sendRedirect("/home");
                return;
            }
        }
        log.info("redirect to /");
        httpResponse.sendRedirect("/");

    }
}