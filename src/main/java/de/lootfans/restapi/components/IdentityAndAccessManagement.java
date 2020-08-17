package de.lootfans.restapi.components;

import java.util.HashMap;

public interface IdentityAndAccessManagement<T, G> {

    public String createUser(T user);

    public G getUserById(String userId);

    public void setPassword(T user, String password);

    public String signInUser(String username, String Password);
}
