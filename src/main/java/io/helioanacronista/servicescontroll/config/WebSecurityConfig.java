package io.helioanacronista.servicescontroll.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Chave secreta usada para assinar os tokens JWT gerados pelo sistema
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Bean para codificar senhas em hash BCrypt
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para converter tokens JWT em objetos Java e vice-versa
    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(jwtSecret);
        return tokenConverter;
    }

    // Bean para armazenar tokens JWT
    @Bean
    JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // Bean para gerenciar autenticações
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
