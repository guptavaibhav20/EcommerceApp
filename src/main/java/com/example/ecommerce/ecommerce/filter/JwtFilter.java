package com.example.ecommerce.ecommerce.filter;

import com.example.ecommerce.ecommerce.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        String username = jwtService.extractUsername(token);
        try {
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (user != null && jwtService.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    authtoken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authtoken);

                }
            }
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);

    }
}
