package com.tutorials.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            try {

                String token = jwt.substring(7); 

                String email = JwtProvider.getEmailFromJwtToken(token);
                String role = JwtProvider.getRoleFromJwtToken(token);

                List<GrantedAuthority> authorities = new ArrayList<>();

                if (role != null && !role.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority(role));
                } else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);


                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                System.out.println("❌ JWT ERROR: " + e.getMessage());
       
            }
        }

        filterChain.doFilter(request, response);
    }
}