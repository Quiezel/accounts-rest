/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.security;

import io.jsonwebtoken.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chalio
 */
@Component
public class JwtTokenProvider {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    @Value("${app/jwtSecret}")
    private String jwtSecret;
    
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        return Jwts.builder()
                .setSubject(Long.toString(usuarioPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        return Long.parseLong(claims.getSubject());
    }
    
    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch(SignatureException ex){
            LOGGER.error("Invalid JWT signature");
        }catch(MalformedJwtException ex){
            LOGGER.error("Invalid JWT token");
        }catch(ExpiredJwtException ex){
            LOGGER.error("Expired JWT token");
        }catch(UnsupportedJwtException ex){
            LOGGER.error("Unsupported JWT token");
        }catch(IllegalArgumentException ex){
            LOGGER.error("JWT claims string is empty.");
        }
        return false;
    }
    
}
