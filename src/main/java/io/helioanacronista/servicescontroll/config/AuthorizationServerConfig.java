package io.helioanacronista.servicescontroll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
//Faz Login
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // Id do cliente que será configurado no método configure(ClientDetailsServiceConfigurer clients)
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    // Senha criptografada do cliente que será configurada no método configure(ClientDetailsServiceConfigurer clients)
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    // Validade do token de acesso em segundos que será configurada no método configure(ClientDetailsServiceConfigurer clients)
    @Value("${jwt.duration}")
    private Integer jwtDuration;

    // Codificador de senhas que será usado para criptografar a senha do cliente no método configure(ClientDetailsServiceConfigurer clients)
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Conversor de tokens de acesso JWT que será configurado no método configure(AuthorizationServerEndpointsConfigurer endpoints)
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    // Armazenamento de tokens JWT que será configurado no método configure(AuthorizationServerEndpointsConfigurer endpoints)
    @Autowired
    private JwtTokenStore tokenStore;

    // Mecanismo de autenticação que será configurado no método configure(AuthorizationServerEndpointsConfigurer endpoints)
    @Autowired
    private AuthenticationManager authenticationManager;

    // Configura as configurações de segurança do servidor de autorização
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    // Configura os clientes que podem se conectar ao servidor de autorização
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .scopes("read", "write")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(jwtDuration);
    }

    // Configura os pontos de extremidade do servidor de autorização
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter);
    }
}
