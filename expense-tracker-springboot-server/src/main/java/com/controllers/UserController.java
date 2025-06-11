package com.controllers;

import com.entities.User;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // this class handles http requests
@RequestMapping("/api/v1/user") //  finds the url path for the controller
public class UserController {

    @Autowired // refers to only one instance
    private UserService userService;

    @GetMapping
    public Optional<User> getUserById(@RequestParam int userId) {
        return userService.getUserById(userId);
    }
}
