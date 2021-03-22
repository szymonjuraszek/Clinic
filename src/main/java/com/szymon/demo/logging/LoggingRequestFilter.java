package com.szymon.demo.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Component
public class LoggingRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(LoggingRequestFilter.class);



//    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
//
//        try {
//            chain.doFilter(cachedBodyHttpServletRequest, res);
//        } finally {
//            logRequestBody(cachedBodyHttpServletRequest, httpServletRequest);
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);

        try {
            filterChain.doFilter(cachedBodyHttpServletRequest, res);
        } finally {
            logRequestBody(cachedBodyHttpServletRequest, httpServletRequest);
        }
    }

    private void logRequestBody(CachedBodyHttpServletRequest request, HttpServletRequest httpServletRequest) throws IOException {

        httpServletRequest.setAttribute("LOGS", new ArrayList<>());
        System.out.println("REQUEST -> Id watku: " + Thread.currentThread().getId());
        System.out.println("REQUEST -> nazwa watku: " + Thread.currentThread().getName());


        if (LoggingBodyValidator.ifLogBody(httpServletRequest)) {
            byte[] buf = request.getInputStream().readAllBytes();

            if (buf.length > 0) {
                try {
                    String requestBody = new String(buf, 0, buf.length, request.getCharacterEncoding());

                    createLogRequest(request, requestBody);
                } catch (Exception e) {
                    logger.error("error in reading request body");
                }
            } else {
                createLogRequest(request, null);
            }
        } else {
            createLogRequest(request, null);
        }
    }

    private void createLogRequest(CachedBodyHttpServletRequest request, @Nullable String body) {
        String logMessage = "Request -> URI: " + request.getServletPath() + " HTTP method: " + request.getMethod() + " Body: " + Optional.ofNullable(body).orElse("{}");

        logger.info(logMessage);

        LogManager.add(request, logMessage);
    }

}
