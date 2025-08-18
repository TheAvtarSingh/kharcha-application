package org.kharcha.app.services;

import java.util.List;

import org.kharcha.app.entities.User;
import org.kharcha.app.exceptions.UserAlreadyExistsException;
import org.kharcha.app.exceptions.UserNotExistsException;
import org.kharcha.app.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String userEmail) {
		User existingUser = userRepository.findUserByUserEmail(userEmail);
		if (existingUser != null) {
			return existingUser;
		}
		throw new UserNotExistsException("User with email " + userEmail + " does not exist.");
		
	}

	@Override
	public User updateUser(User newUser) {
		User existingUser = userRepository.findUserByUserEmail(newUser.getUserEmail());
		
		if (existingUser != null) {
			String hashedPassword = BCrypt.hashpw(newUser.getUserPassword(), BCrypt.gensalt());
			existingUser.setUserPassword(hashedPassword);
			return userRepository.save(existingUser);
		}

		throw new UserNotExistsException("User with email " + newUser.getUserEmail() + " does not exist.");
	}

	@Override
	public User removeUser(String userEmail) {
		User user = userRepository.findUserByUserEmail(userEmail);
		if (user != null) {
			userRepository.delete(user);
			return user;
		}
		throw new UserNotExistsException("User with email " + userEmail + " does not exist.");
	}

	@Override
	public User createUser(User user) {
		User existingUser = userRepository.findUserByUserEmail(user.getUserEmail());
		if (existingUser != null) {
			throw new UserAlreadyExistsException("User with email " + user.getUserEmail() + " already exists.");
		}
		String hashedPassword = BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt());
	    user.setUserPassword(hashedPassword);
		return userRepository.save(user);
	}

	@Override
	public Boolean verifyUser(String email,String password) {
		// TODO Auto-generated method stub
		User existingUser = userRepository.findUserByUserEmail(email);
		if(existingUser != null) {
			return BCrypt.checkpw(password, existingUser.getUserPassword());
		}
		throw new UserNotExistsException("User with email " + email + " does not exist.");
	}
}
