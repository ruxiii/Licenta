package org.example.licenta.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.licenta.services.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            String jwtToken = (String) auth.getCredentials();
            if (jwtToken == null) {
                jwtToken = tokenService.generateJwt(auth);
                auth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), jwtToken, auth.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        }
        filterChain.doFilter(request, response);
    }


}
