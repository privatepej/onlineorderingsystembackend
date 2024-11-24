package org.acumen.training.codes.services;

import org.acumen.training.codes.dao.UserDao;
import org.acumen.training.codes.dto.UserDTO;
import org.acumen.training.codes.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	public boolean registerUser(UserDTO userDTO) {
        Users users = mapDtoToEntity(userDTO);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return userDao.createUser(users);
    }
	
	public boolean validateUser(String email, String rawPassword) {
	    Users user = userDao.findByEmail(email);
	    if (user == null) {
	        System.out.println("User not found for email: " + email);
	        return false;
	    }
        System.out.println("user.getPassword() " + user.getPassword());
        System.out.println("rawPassword " + rawPassword);

	    boolean isPasswordMatch = passwordEncoder.matches(rawPassword, user.getPassword());
	    if (!isPasswordMatch) {
	        System.out.println("Password did not match for email: " + email);
	    }
	    return isPasswordMatch;
	}

	

    public UserDTO findUserByEmail(String email) {
        Users user = userDao.findByEmail(email);
        if (user == null) {
            return null;
        }
        return mapEntityToDto(user);
    }
    
    
    public boolean updateUser(UserDTO userDTO) {
        Users existingUser = userDao.findByEmail(userDTO.getEmail());
        if (existingUser == null) {
            return false;
        }

        if (userDTO.getUsername() != null) {
            existingUser.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getAddress() != null) {
            existingUser.setAddress(userDTO.getAddress());
        }
        
        return userDao.updateUser(existingUser);
    }
    
//    public boolean loginUser(String email, String password) {
//        Users user = userDao.findByEmail(email);
//        if (user != null && user.getPassword().equals(password)) {
//            return true;
//        }
//        return false;
//    }


    private Users mapDtoToEntity(UserDTO userDTO) {
        Users users = new Users();
        users.setUsername(userDTO.getUsername());
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        users.setAddress(userDTO.getAddress());
        users.setRole(userDTO.getRole());
        return users;
    }

    private UserDTO mapEntityToDto(Users users) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setUsername(users.getUsername());
        userDTO.setEmail(users.getEmail());
        userDTO.setAddress(users.getAddress());
        userDTO.setRole(users.getRole());
        return userDTO;
    }
}
