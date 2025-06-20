package com.example.expense_tracker_server.services;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.expense_tracker_server.entities.User;
import com.example.expense_tracker_server.repositories.UserRepository;

@Service // This annotation indicates that this class is a service component in the Spring context
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(int userId){
        logger.info("Getting the user by id: " + userId);
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email){
        logger.info("Getting the user by email: " + email);
        return userRepository.findByEmail(email);
    }
    
}
