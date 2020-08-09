package de.lootfans.restapi.config;

import io.minio.MinioClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.keycloak.admin.client.Keycloak;

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


    @Bean
    public Keycloak keycloakClient() {

        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(userName)
                .password(password)
                .clientId(client)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

}
