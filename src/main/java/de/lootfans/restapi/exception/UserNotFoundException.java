package de.lootfans.restapi.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super(String.format("User with Id %d not found", id));
    }

    public UserNotFoundException(String name) {
        super(String.format("User with Id %s not found", name));
    }
}
