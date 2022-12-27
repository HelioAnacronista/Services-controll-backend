package io.helioanacronista.servicescontroll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

// Configuração do servidor de recursos
@Configuration
@EnableResourceServer
// Habilita a segurança de método global com anotações @PreAuthorize e
@EnableGlobalMethodSecurity(prePostEnabled = true)

//Controler de acesso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // Origens permitidas para CORS
    @Value("${cors.origins}")
    private String corsOrigins;

    // Ambiente ativo da aplicação (desenvolvimento, produção, etc)
    @Autowired
    private Environment env;

    // Armazenamento de token
    @Autowired
    private JwtTokenStore tokenStore;

    // Configura o armazenamento de tokens do servidor de recursos
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    // Configura a segurança do servidor de recursos
    @Override
    public void configure(HttpSecurity http) throws Exception {

        // Desabilita a proteção de frame para permitir o acesso ao console H2 durante os testes
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // Permite todas as requisições a qualquer recurso
        http.authorizeRequests().anyRequest().permitAll();

        // Habilita o suporte a CORS
        http.cors().configurationSource(corsConfigurationSource());
    }

    // Configura as configurações de CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        // Divide a string de origens permitidas em um array
        String[] origins = corsOrigins.split(",");

        // Cria uma configuração de CORS
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Arrays.asList(origins));
        corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // Cria uma fonte de configuração baseada em URL para o CORS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    // Registra o filtro de CORS para ser aplicado em todas as requisições
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean
                = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

