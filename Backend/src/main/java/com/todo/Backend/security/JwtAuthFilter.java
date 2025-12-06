package com.todo.Backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final Jwtutil jwtUtil;

    public JwtAuthFilter(Jwtutil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();

            // basic validation: parse & verify signature/expiry
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                Long userId = jwtUtil.extractUserId(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // For now we use email as principal and set userId in details.
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    authenticationToken.setDetails(userId);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } else {
                // token invalid or expired — we do not authenticate; proceed so later security chain can reject
            }
        }

        filterChain.doFilter(request, response);
    }
}
