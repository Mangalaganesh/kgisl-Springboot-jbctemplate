package com.Dolphin.SpringAngular.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dolphin.SpringAngular.DTO.LoginRequest;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Hardcoded username and password for demo purposes
        String hardcodedUsername = "user";
        String hardcodedPassword = "password";

        if (loginRequest.getUsername().equals(hardcodedUsername) && loginRequest.getPassword().equals(hardcodedPassword)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
