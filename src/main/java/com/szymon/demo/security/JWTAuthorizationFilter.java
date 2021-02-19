package com.szymon.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.szymon.demo.security.SecurityConstants.*;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {

        String token = req.getHeader(HEADER_STRING);

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        logger.info("Check JWT token");

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token, req);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletRequest request) {

        DecodedJWT decodedJWT = JWT.decode(token.replace(TOKEN_PREFIX, ""));
        try {
            decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));

            if (request.getServletPath().equals(PATH_TO_REFRESH_TOKEN)) {
                throw new Exception("Can't refresh token if time isn't expired!");
            }

            if (decodedJWT.getSubject() != null) {
                logger.info("Authorization Completed");

                return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null,
                        getAuthorities(decodedJWT.getClaims().get(AUTHORITY_CLAIMS_NAME).asList(String.class)));
            }
        } catch (TokenExpiredException ex) {
            logger.error("Token expired");

            if (decodedJWT.getClaim(CAN_REFRESH_CLAIM_NAME).as(Boolean.class) &&
                    new Date(decodedJWT.getClaim(TIME_TO_REFRESH_CLAIM_NAME).as(Long.class)).after(new Date(System.currentTimeMillis())) &&
                    request.getServletPath().equals(PATH_TO_REFRESH_TOKEN)) {
                return allowForRefreshToken(decodedJWT, request);
            } else {
                request.setAttribute("exception", ex);
            }

        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    private List<GrantedAuthority> getAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }

        return authorities;
    }

    private UsernamePasswordAuthenticationToken allowForRefreshToken(DecodedJWT decodedJWT, HttpServletRequest request) {

        request.setAttribute(REQUEST_ATTRIBUTE_CLAIMS_NAME, decodedJWT.getClaims().get(AUTHORITY_CLAIMS_NAME).asList(String.class));
        request.setAttribute(REQUEST_ATTRIBUTE_USER_NAME, decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(
                null, null, null);
    }
}