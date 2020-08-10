package de.lootfans.restapi.config;

import de.lootfans.restapi.service.IAMService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.keycloak.admin.client.Keycloak;

import java.util.concurrent.TimeUnit;

@Configuration
public class KeycloakAdminConfig {

    @Value("${keycloakadmin.realm}")
    String realm;
    @Value("${keycloakadmin.username}")
    String userName;
    @Value("${keycloakadmin.password}")
    String password;
    @Value("${keycloakadmin.client}")
    String client;
    @Value("${keycloakadmin.credentials.secret}")
    String secret;
    @Value("${keycloak.auth-server-url}")
    String serverUrl;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IAMService.class);


    @Bean
    public Keycloak keycloakClient() {

        LOGGER.info("Initalizing keycloak Client with these credentials:\n" +
                "realm: {}\nusername: {}\npassword: {}\nclient: {}\nsecret: {}\nserverurl: {}\n",
                realm, userName, password, client, secret, serverUrl);

        ResteasyClient resteasyClient = new ResteasyClientBuilder().connectionPoolSize(10).build();

        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(userName)
                .password(password)
                .clientId(client)
                .clientSecret(secret)
                .grantType(OAuth2Constants.PASSWORD)
                .resteasyClient(resteasyClient)
                .build();
    }

}
