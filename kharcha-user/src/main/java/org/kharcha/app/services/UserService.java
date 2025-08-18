package org.kharcha.app.services;

import java.util.List;

import org.kharcha.app.entities.User;


public interface UserService {
public List<User> getUsers();
public User getUserByEmail(String userEmail);
public User updateUser(User newUser);
public User removeUser(String userEmail);
public User createUser(User user);
public Boolean verifyUser(String email,String password);

}
