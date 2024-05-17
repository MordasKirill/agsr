package com.agsr.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/patients/**").hasAnyRole("PRACTITIONER", "PATIENT")
                        .requestMatchers(HttpMethod.POST, "/api/patients").hasRole("PRACTITIONER")
                        .requestMatchers(HttpMethod.PUT, "/api/patients/**").hasRole("PRACTITIONER")
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("PRACTITIONER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
           var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
           var roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

           return Stream.concat(authorities.stream(), roles.stream()
                   .filter(role -> role.startsWith("ROLE_"))
                   .map(SimpleGrantedAuthority::new))
                   .map(GrantedAuthority.class::cast)
                   .toList();
       });
       return converter;
    }
}


