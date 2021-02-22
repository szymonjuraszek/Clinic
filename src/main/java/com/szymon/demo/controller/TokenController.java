package com.szymon.demo.controller;

import com.szymon.demo.security.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.szymon.demo.security.SecurityConstants.*;

@RestController
@RequestMapping(path = "/token")
public class TokenController {

    private final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping(path = "/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = JWTUtil.generateRefreshedJWT(
                (List<String>) request.getAttribute(REQUEST_ATTRIBUTE_CLAIMS_NAME),
                (String) request.getAttribute(REQUEST_ATTRIBUTE_USER_NAME));

        logger.info("Refreshed token");

        Cookie accessToken = new Cookie(ACCESS_TOKEN_NAME, token);
        response.addCookie(accessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
