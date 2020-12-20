package com.pagantis.pagacoin.controller;

import com.pagantis.pagacoin.model.User;
import com.pagantis.pagacoin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * getAllUsers
     *
     * @return <List<User>> lista de usuarios registrados
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();

            userRepository.findAll().forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * getUserByEmail
     * @param email
     * @return Usuario
     */
    @GetMapping("/getUser/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * createUser
     * @param user
     * @return Usuario creado
     */
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepository.save(new User(user.getEmail(), user.getPassword(), user.getName(), user.getLastname()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
