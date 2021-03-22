package com.szymon.demo.logging;

import com.szymon.demo.collections.Log;
import com.szymon.demo.repository.log.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LoggingResponseFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

    private final LogRepository logRepository;

    public LoggingResponseFilter(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
//
//        try {
//            filterChain.doFilter(servletRequest, responseCacheWrapperObject);
//        } finally {
//            logResponseBody(responseCacheWrapperObject, (HttpServletRequest) servletRequest);
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        try {
            filterChain.doFilter(servletRequest, responseCacheWrapperObject);
        } finally {
            logResponseBody(responseCacheWrapperObject, (HttpServletRequest) servletRequest);
        }
    }

    private void logResponseBody(ContentCachingResponseWrapper response, HttpServletRequest httpServletRequest) throws IOException {

        List<Log> logs = (ArrayList<Log>) httpServletRequest.getAttribute("LOGS");
        System.out.println("RESPONSE -> Id watku: " + Thread.currentThread().getId());
        System.out.println("RESPONSE -> nazwa watku: " + Thread.currentThread().getName());

        if (LoggingBodyValidator.ifLogBody(httpServletRequest)) {
            byte[] buf = response.getContentAsByteArray();

            if (buf.length > 0) {
                try {
                    String responseBody = new String(buf, 0, buf.length, response.getCharacterEncoding());

                    createLogResponse(httpServletRequest, response.getStatus(), responseBody);
                } catch (Exception e) {
                    logger.error("error in reading response body");
                }
            } else {
                createLogResponse(httpServletRequest, response.getStatus(), null);
            }
        } else {
            createLogResponse(httpServletRequest, response.getStatus(), null);
        }

//        List<Log> logs = (ArrayList<Log>) httpServletRequest.getAttribute("LOGS");

//        logRepository.saveAll(logs);

        response.copyBodyToResponse();
    }

    private void createLogResponse(HttpServletRequest httpServletRequest, int status, @Nullable String body) {
        String logMessage = "Response -> URI: " + httpServletRequest.getServletPath() + " HTTP status: " + status + " Body: " + Optional.ofNullable(body).orElse("{}");

        logger.info(logMessage);

        LogManager.add(httpServletRequest, logMessage);
    }
}
