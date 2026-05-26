package com.substring.auth.auth_app_backend.security;


import com.substring.auth.auth_app_backend.entities.Role;
import com.substring.auth.auth_app_backend.entities.User;
import com.substring.auth.auth_app_backend.helpers.UserHelper;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {

           // token extract and validate then authentication create and then set inside security context.


            String token = header.substring(7);

            //Check for access token
            if(!jwtService.isAccessToken(token)){

                //if you want to pass message then pass later.
                filterChain.doFilter(request, response);
                return;
            }

            try {

            Jws<Claims> parse = jwtService.parse(token);

            Claims payload = parse.getPayload();
            String subject = payload.getSubject();
                UUID userUuid = UserHelper.parseUUID(subject);

                userRepository.findById(userUuid)
                        .ifPresent((User user) -> {

                            //Check for user enable or not.
                            if (!user.isEnable()) {

                                // user is found from database);
                                //
                                List<GrantedAuthority> authorities =
                                        user.getRoles()
                                                .stream()
                                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                        .collect(Collectors.toList());


                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null,
                                        authorities
                                );

                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                //final line: to set the authentication and security context
                                if (SecurityContextHolder.getContext().getAuthentication() == null)
                                    SecurityContextHolder.getContext().setAuthentication(authentication);


                            }

                        });


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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        return request.getRequestURI().startsWith("/api/v1/auth");
    }
}
