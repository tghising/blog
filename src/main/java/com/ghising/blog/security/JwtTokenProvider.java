package com.ghising.blog.security;

import com.ghising.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.blog.jwt-secret}")
    private String jwtSecret;

    @Value("${app.blog.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // generate token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    //get username from the token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Signature.");
        } catch (IllegalArgumentException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
