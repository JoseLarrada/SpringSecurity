package com.ejercicios.SpringSecurity.Config;

import com.ejercicios.SpringSecurity.Repository.UserRepository;
import com.ejercicios.SpringSecurity.Service.JwtService;
import com.ejercicios.SpringSecurity.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. Obtener el Header que contiene el JWT
        String authHeader=request.getHeader("Authorization"); //Saca el bearer
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //2. Obtener el jwt desde el header
        String jwt = authHeader.split(" ")[1];

        //3. Obtener el username desde el jwt
        String username=jwtService.extractUsername(jwt);

        //4. Setear un objeto dentro del SecurityContext
        User user=userRepository.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                username, null,user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //5. Ejecutar el resto de filtros
        filterChain.doFilter(request,response);
    }
}
