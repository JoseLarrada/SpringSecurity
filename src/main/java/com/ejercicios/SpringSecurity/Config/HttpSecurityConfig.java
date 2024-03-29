package com.ejercicios.SpringSecurity.Config;

import com.ejercicios.SpringSecurity.Util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())//Desactivar la vulnerabilidad web que viene habilitado por defecto
                .sessionManagement(sessionManConfig -> sessionManConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Las sesiones ya no van a tener estados
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {
                    //Metodos publicos
                   authConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                   authConfig.requestMatchers(HttpMethod.GET,"/auth/public-access").permitAll();
                   authConfig.requestMatchers("/error").permitAll();

                    //Metodos privados
                    authConfig.requestMatchers(HttpMethod.GET,"/products").hasAnyAuthority(Permission.READ_ALL_PRODUCT.name()); //Solo pueden acceder las personas que cumplan con ese rol
                    authConfig.requestMatchers(HttpMethod.POST,"/products").hasAnyAuthority(Permission.SAVE_ONE_PRODUCT.name());

                    //Denegar las peticiones que no esten dentro de los requestMatchers
                    authConfig.anyRequest().denyAll();
                });
        return http.build();
    }
}
