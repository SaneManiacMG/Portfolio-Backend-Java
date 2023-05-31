package com.smworks.backendportfolio.util;

import com.smworks.backendportfolio.models.Login;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final long EXPIRE_DURATION = 1000 * 60 * 60 * 24;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(Login login) {
        return Jwts.builder()
                .setSubject(login.getUsername())
                .setIssuer("SaneManiacWorks")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT Expired\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Token is empty\n" + e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("JWT is invalid\n" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT is not supported\n" + e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("Signature validation failed\n" + e.getMessage());
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
