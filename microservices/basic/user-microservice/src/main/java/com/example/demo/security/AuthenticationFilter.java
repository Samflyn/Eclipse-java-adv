package com.example.demo.security;

import com.example.demo.dtos.UserDto;
import com.example.demo.model.EmailPassword;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            EmailPassword emailPassword = new ObjectMapper()
                    .readValue(request.getInputStream(), EmailPassword.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            emailPassword.getEmail(),
                            emailPassword.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String name = ((User) authResult.getPrincipal()).getName();
        UserDto userDto = new UserDto();
        String token = Jwts.builder()
                .setSubject(userDto.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("864000")))
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
        response.addHeader("token",token);
        response.addHeader("email",userDto.getEmail());
    }
}
