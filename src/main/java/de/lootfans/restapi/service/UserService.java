package de.lootfans.restapi.service;

import de.lootfans.restapi.repository.UserRepository;
import de.lootfans.restapi.specification.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import de.lootfans.restapi.exception.UserNotFoundException;
import de.lootfans.restapi.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IAMService iamService;

    @Autowired
    PasswordEncoder passwordEncoder;


    public List<User> getUsers(){
        return userRepository.findAll();
    }


    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findOne(UserSpecifications.getUserByID(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(@RequestBody User user) {

        userRepository.save(user);

        String userId = iamService.createUser(user);

        user.setIamID(userId);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
