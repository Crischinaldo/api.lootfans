package de.lootfans.restapi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.lootfans.restapi.config.KeycloakSecurityConfig;
import de.lootfans.restapi.response.TokenResponse;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.lootfans.restapi.exception.UserNotFoundException;
import de.lootfans.restapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import de.lootfans.restapi.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/users"
    )
    public ResponseEntity<List<User>> getUsers(){
        List<User> usersResult = userService.getUsers();

        if (!usersResult.isEmpty()) {
            return new ResponseEntity<>(usersResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(usersResult, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/users/{id}"
    )

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User usr = userService.getUserById(id);

        if (usr != null) {
            return new ResponseEntity<>(usr, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(User user) {
        User usr = userService.createUser(user);

        if (usr != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> signInUser(String userName, String password) {

        String token = userService.signInUser(userName, password);

        Gson gson = new Gson();
        TokenResponse response = new TokenResponse();
        response.token = token;

        String jsonString = gson.toJson(response);

        return new ResponseEntity<>(jsonString, HttpStatus.CREATED);
    }

}
