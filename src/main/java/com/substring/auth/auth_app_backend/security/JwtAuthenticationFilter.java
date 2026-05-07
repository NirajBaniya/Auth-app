package com.substring.auth.auth_app_backend.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {

           // token extract and validate then authentication create and then set inside security context.


            String token = header.substring(7);

            try {

            Jws<Claims> parse = jwtService.parse(token);

            }
            catch (ExpiredJwtException e) {
                e.printStackTrace();
            }

            catch(MalformedJwtException e) {
                e.printStackTrace();
            }

            catch(JwtException e) {
                e.printStackTrace();
            }

            catch(Exception e) {
                e.printStackTrace();
            }

        }

         filterChain.doFilter(request, response);

    }
}
