package com.claretiano.estoque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity htpp) throws Exception{
        htpp
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register"
                        ).permitAll()

                        .requestMatchers("/v1/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/v1/produto/**",
                                "/v1/categoria/**",
                                "/v1/inventario/**",
                                "/v1/movimentacao-estoque/**"
                        )
                        .hasAnyRole("USER", "ADMIN")

                        .anyRequest()
                        .authenticated()
                ).httpBasic(Customizer.withDefaults());

        return htpp.build();
    }
}
