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
public class RoleFilter implements Filter {
    static final Logger log = LoggerFactory.getLogger(MainController.class);
    //    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        String requestUri = httpRequest.getRequestURI();
//        log.info(requestUri);
//        if (!requestUri.startsWith("/admin")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        User user = (User) httpRequest.getSession().getAttribute("userDTO");
//        if (user != null) {
//            if (user.hasRole("ROLE_ADMIN")) {
//                log.info("User has required role (ROLE_ADMIN)");
//                chain.doFilter(request, response);
//                return;
//            }            else {
//                log.info("User does not have required role (ROLE_ADMIN)");
//            }
//            if (user.hasRole("ROLE_USER")) {
//                log.info("User has role (ROLE_USER)");
//                log.info("redirect to /home");
//                httpResponse.sendRedirect("/home");
//                return;
//            }
//        }
//        log.info("redirect to /");
//        httpResponse.sendRedirect("/");
//
//    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if ((requestURI.startsWith("/admin") || requestURI.startsWith("/home") || requestURI.startsWith("/blog")
                || requestURI.startsWith("/about"))
                && httpRequest.getSession().getAttribute("userDTO") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            log.info("user is null redirect to login1");
            return;
        }

        if (requestURI.equals("/") || requestURI.equals("/login") || requestURI.equals("/registration")) {
            chain.doFilter(request, response);
            return;
        }
        User userDTO = (User) ((HttpServletRequest) request).getSession().getAttribute("userDTO");

        if (requestURI.startsWith("/admin") && !userDTO.hasRole("ROLE_ADMIN")) {
//            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            log.info("Must be admin!");
            httpResponse.sendRedirect("/home");
            return;
        }
        else if (requestURI.startsWith("/blog") || requestURI.startsWith("/home")
                && userDTO.hasRole("ROLE_USER") || userDTO.hasRole("ROLE_ADMIN")) {
            chain.doFilter(request, response);
            return;
        }
//        else if (requestURI.startsWith("/blog") || requestURI.startsWith("/home")
//                && !userDTO.hasRole("ROLE_USER") && !userDTO.hasRole("ROLE_ADMIN")) {
//            log.info("redirect to login2");
//            httpResponse.sendRedirect("/login");
//            return;
//        }
        chain.doFilter(request, response);
    }
}