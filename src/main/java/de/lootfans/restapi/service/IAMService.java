package de.lootfans.restapi.service;

import de.lootfans.restapi.components.IdentityAndAccessManagement;
import de.lootfans.restapi.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
public class IAMService implements IdentityAndAccessManagement<User, UserResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IAMService.class);

    @Autowired
    Keycloak keycloak;

    @Value("${keycloak.realm}")
    String realmScope;

    /**
     * Creates a keycloak user in a defined realm and assign the user role
     * to the created user.
     *
     * @param user a {@code User instance}
     * @return userID from created Keycloakuser
     */
    @Override
    public String createUser(User user) {

        LOGGER.info("Create KeyCloak User");

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setEmail(user.getEmail());
        kcUser.setUsername(user.getUserName());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEnabled(Boolean.TRUE);

        RealmResource realm = keycloak.realm(realmScope);

        Response response = realm.users().create(kcUser);

        LOGGER.info("User {} created with Response {}", kcUser, response);

        RoleRepresentation userRole = realm.roles()
                .get("user")
                .toRepresentation();

        String userId = realm.users().search(user.getUserName()).get(0).getId();

        // assign userrole "user" to new User
        this.getUserById(userId).roles().realmLevel().add(Collections.singletonList(userRole));

        return userId;
    }

    /**
     * Get a User by the userid
     *
     * @param userId Id of the user
     * @return a {@code UserResource} instance
     */
    @Override
    public UserResource getUserById(String userId) {

        LOGGER.info("Get KeyCloak User by ID");

        UsersResource users = keycloak.realm(realmScope).users();
        UserResource user = users.get(userId);

        LOGGER.info("Return User {} with id {}", user, userId);

        return user;
    }

    /**
     * Sets password of a keycloak user.
     *
     * @param user A {@code User} instance
     * @param password passwort to be setted
     */
    @Override
    public void setPassword(User user, String password) {

        UsersResource users = keycloak.realm(realmScope).users();

        CredentialRepresentation passwordCred = new CredentialRepresentation();

        passwordCred.setTemporary(false); // User doesnt need to set password himselve again
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        users.get(user.getIamID()).resetPassword(passwordCred);

    }
}
