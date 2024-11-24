package org.acumen.training.codes.controller;

import org.acumen.training.codes.dto.UserDTO;
import org.acumen.training.codes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegisterController {

	 @Autowired
	 private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        boolean isCreated = userService.registerUser(userDTO);
        if (isCreated) {
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	    
}
