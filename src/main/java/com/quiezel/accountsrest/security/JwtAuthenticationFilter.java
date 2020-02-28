/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.security;

import java.io.IOException;
import java.security.Security;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Chalio
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(hsr);
            
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(hsr));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }
        fc.doFilter(hsr, hsr1);
    }
    
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    
}
