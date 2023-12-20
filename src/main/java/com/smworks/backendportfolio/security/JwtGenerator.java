package com.smworks.backendportfolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.smworks.backendportfolio.models.entities.UserBase;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    public String generateToken(Authentication authentication) {
        String userId = ((UserBase) authentication.getPrincipal()).getUserId();
        Set<String>authorities = new HashSet<>();

        if (authentication.getPrincipal() instanceof UserBase) {
            UserBase user = (UserBase) authentication.getPrincipal();
            authorities = user.getAuthorities().stream().map(GrantedAuthority :: getAuthority)
                    .collect(Collectors.toSet());
        }

        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        
        String token = Jwts.builder()
                .setSubject(userId)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setIssuer("SaneManiacWorks")
                .setAudience("PortfolioFrontend")
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECRET)
                .compact();
        return token;
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // TODO: handle exception, currently just prints out Invalid JWT token.
            
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token");
        }
    }
}
