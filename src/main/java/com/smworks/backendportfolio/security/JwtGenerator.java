package com.smworks.backendportfolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.smworks.backendportfolio.models.entities.Role;
import com.smworks.backendportfolio.models.entities.UserBase;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    public String generateToken(Authentication authentication) {

        // TODO: Make the subject the user id instead of username

        String userId = ((UserBase) authentication.getPrincipal()).getUserId();
        Set<Role> roles = new HashSet<>();

        if (authentication.getPrincipal() instanceof UserBase) {
            UserBase user = (UserBase) authentication.getPrincipal();
            roles = user.getAuthorities().stream().map(role -> new Role(role.getAuthority()))
                    .collect(Collectors.toSet());
        }

        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        
        String token = Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setIssuer("SaneManiacWorks")
                .setAudience("PortfolioFrontend")
                .setExpiration(expiryDate)
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
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token");
        }
    }
}
