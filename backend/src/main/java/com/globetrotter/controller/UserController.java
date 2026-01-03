package com.globetrotter.controller;

import com.globetrotter.model.User;
import com.globetrotter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        try {
            User user = userService.getCurrentUser();
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody User userDetails) {
        try {
            Long userId = userService.getCurrentUserId();
            User updatedUser = userService.updateUser(userDetails, userId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

