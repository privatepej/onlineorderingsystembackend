package org.acumen.training.codes.controller;

import org.acumen.training.codes.dto.UserDTO;
import org.acumen.training.codes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/find")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        UserDTO userDTO = userService.findUserByEmail(email);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping(path = "/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        boolean isUpdated = userService.updateUser(userDTO);
        if (isUpdated) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User update failed", HttpStatus.BAD_REQUEST);
        }
    }
    

}
