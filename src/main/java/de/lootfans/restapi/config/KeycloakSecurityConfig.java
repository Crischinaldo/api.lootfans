package de.lootfans.restapi.config;


import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/* https://smartling.github.io/keycloak/docs/1.2.1.Smartling-SNAPSHOT/reference/en-US/html_single/#spring-boot-adapter-installation */


@KeycloakConfiguration
@EnableWebSecurity
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();

        // adding proper authority mapper for prefixing role with "ROLE_"
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    /**
     * Provide a session authentication strategy bean which should be of type
     * RegisterSessionAuthenticationStrategy for public or confidential applications
     * and NullAuthenticatedSessionStrategy for bearer-only applications.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    /**
     * Use properties in application.properties instead of keycloak.json
     */
    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Secure appropriate endpoints
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.cors().and().csrf().disable()
                .authorizeRequests()
            //    .antMatchers("api/v1/files").hasRole("user") // only user with role user are allowed to access
           //     .antMatchers("api/v1/users").hasRole("admin") // only user with role user are allowed to access
                .anyRequest().permitAll();
    }


    @Value("${keycloakadmin.realm}")
    String realm;
    @Value("${keycloakadmin.client}")
    String client;
    @Value("${keycloakadmin.credentials.secret}")
    String secret;
    @Value("${keycloak.auth-server-url}")
    String serverUrl;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(KeycloakSecurityConfig.class);


    @Bean
    public Keycloak keycloakClient() {

        LOGGER.info("Initalizing keycloak Admin Client with these credentials:\n" +
                        "realm: {}\nclient: {}\nsecret: {}\nserverurl: {}\n",
                realm, client, secret, serverUrl);

        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(client)
                .clientSecret(secret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}