package com.szymon.demo.security;

import com.auth0.jwt.JWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.szymon.demo.security.SecurityConstants.*;

public class JWTUtil {

    public static String generateNewJWT(Authentication authResult) {
        return JWT.create()
                .withSubject(((UserDetails) authResult.getPrincipal()).getUsername())
                .withClaim(CAN_REFRESH_CLAIM_NAME, true)
                .withClaim(TIME_TO_REFRESH_CLAIM_NAME, System.currentTimeMillis() + EXPIRATION_TIME + ADDITIONAL_TIME_TO_REFRESH)
                .withArrayClaim(AUTHORITY_CLAIMS_NAME, ((MyUserDetails) authResult.getPrincipal())
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public static String generateRefreshedJWT(List<String> claims, String username) {
        return JWT.create()
                .withSubject(username)
                .withClaim(CAN_REFRESH_CLAIM_NAME, false)
                .withArrayClaim(AUTHORITY_CLAIMS_NAME, claims.stream()
                        .toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }
}
