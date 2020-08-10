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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class IAMService implements IdentityAndAccessManagement<User, UserResource> {

    @Autowired
    Keycloak keycloak;

    @Value("${keycloak.realm}")
    String realmScope;

    @Override
    public String createUser(User user) {

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setEmail(user.getEmail());
        kcUser.setUsername(user.getUserName());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEnabled(Boolean.TRUE);

        RealmResource realm = keycloak.realm(realmScope);

        Response response = realm.users().create(kcUser);

        RoleRepresentation userRole = realm.roles()
                .get("user")
                .toRepresentation();

        String userId = realm.users().search(user.getUserName()).get(0).getId();

        // assign userrole "user" to new User
        this.getUserById(userId).roles().realmLevel().add(Collections.singletonList(userRole));

        return userId;
    }

    @Override
    public UserResource getUserById(String userId) {
        UsersResource users = keycloak.realm(realmScope).users();

        return users.get(userId);
    }

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
