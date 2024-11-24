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
public class LoginController {


	 @Autowired
	 private UserService userService;
	 
	 @PostMapping(path = "/login")
	 public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
	     System.out.println("Attempting to login user with email: " + userDTO.getEmail());
	     boolean isValidUser = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
	     if (isValidUser) {
	         System.out.println("Login successful for user: " + userDTO.getEmail());
	         return new ResponseEntity<>("Login successful", HttpStatus.OK);
	     } else {
	         System.out.println("Login failed for user: " + userDTO.getEmail());
	         return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
	     }
	 }

	
}
