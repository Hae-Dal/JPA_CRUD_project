package com.sparta.jpa_crud_project.filter;

import com.sparta.jpa_crud_project.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token is missing");
            return;
        }

        token = token.substring(7); // "Bearer " 제거

        if (jwtUtil.isTokenExpired(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
            return;
        }

        String role = jwtUtil.extractUserRole(token);
        if (request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")) {
            if (!"ADMIN".equals(role)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to modify or delete schedules");
                return;
            }
        }

        // Add additional logic for setting authentication context if needed

        filterChain.doFilter(request, response);
    }
}
