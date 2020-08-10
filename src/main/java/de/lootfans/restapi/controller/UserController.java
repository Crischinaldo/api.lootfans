package de.lootfans.restapi.controller;

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
@RequestMapping("/api/v1")
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
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}
