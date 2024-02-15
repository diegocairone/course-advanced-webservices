package com.cairone.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static JwtAuthenticationToken geJwtAuthenticationToken() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        return (JwtAuthenticationToken) ctx.getAuthentication();
    }

    public static Jwt getJwt() {
        JwtAuthenticationToken auth = geJwtAuthenticationToken();
        return (Jwt) auth.getPrincipal();
    }

    public static UUID getUserId() {
        return UUID.fromString(getJwt().getSubject());
    }
}
