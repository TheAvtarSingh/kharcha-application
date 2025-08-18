package org.kharcha.app.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kharcha.app.entities.User;
import org.kharcha.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/ping")
    public ResponseEntity<Object> serverStatus() {
    	 Map<String, Object> map = new HashMap<>();
	        map.put("timestamp", LocalDateTime.now());
	        map.put("service", applicationName);
	        map.put("status", "Server is Up !!");
	       
	        return new ResponseEntity<>(map, HttpStatus.OK);
	 }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable("email") String email) {
        User deletedUser = userService.removeUser(email);
        return ResponseEntity.ok(deletedUser);
    }
    
    @GetMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
    	 Map<String, Object> map = new HashMap<>();
	        map.put("timestamp", LocalDateTime.now());
	        map.put("status", userService.verifyUser(user.getUserEmail(), user.getUserPassword()));
	        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
