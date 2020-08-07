package org.web.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.web.restapi.exception.UserNotFoundException;
import org.web.restapi.model.User;
import org.web.restapi.repository.UserRepository;
import org.web.restapi.specification.UserSpecifications;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getUsers(){
        return userRepository.findAll();
    }


    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findOne(UserSpecifications.getUserByID(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User add(@RequestBody User user) {
        return userRepository.save(user);
    }
}
