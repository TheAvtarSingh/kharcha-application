package org.kharcha.app.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.kharcha.app.communications.AccountClient;
import org.kharcha.app.entities.User;
import org.kharcha.app.exceptions.UserAlreadyExistsException;
import org.kharcha.app.exceptions.UserNotExistsException;
import org.kharcha.app.repositories.UserRepository;
import org.kharcha.kharcha.common.dtos.AccountDTO;
import org.kharcha.kharcha.common.types.AccountType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountClient accountClient;

	@Value("${service.user.header}")
	private String header;

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
	public Boolean findUserByEmail(String userEmail) {
		return userRepository.findUserByUserEmail(userEmail) != null;
	}

	@Override
	public User updateUser(String userEmail, String oldPassword, String newPassword) {
		User existingUser = userRepository.findUserByUserEmail(userEmail);

		if (existingUser == null) {
			throw new UserNotExistsException("User with email " + userEmail + " does not exist.");
		}

		// Validate old password
		if (!BCrypt.checkpw(oldPassword, existingUser.getUserPassword())) {
			throw new IllegalArgumentException("Old password does not match.");
		}

		// Ensure new password is not empty
		if (newPassword == null || newPassword.trim().isEmpty()) {
			throw new IllegalArgumentException("New password cannot be empty.");
		}

		// Hash and update password
		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		existingUser.setUserPassword(hashedPassword);

		return userRepository.save(existingUser);
	}

	@Override
	public User removeUser(String userEmail) {
		User user = userRepository.findUserByUserEmail(userEmail);
		if (user != null) {
			List<AccountDTO> accounts = accountClient.getAllAcountsLinkedWithEmail(
					userEmail,
					header
			);

			// 2. Delete each account (based on accountType)
			if (accounts != null && !accounts.isEmpty()) {
				for (AccountDTO account : accounts) {
					accountClient.deleteAcountsLinkedWithEmail(
							userEmail,
							account.getAccountType(),
							header
					);
				}
			}
			// 3. Delete the user
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
		User savedUser = userRepository.save(user);
		AccountDTO accountDTO = new AccountDTO(
				null,
				savedUser.getUserEmail(),
				savedUser.getUserId(),
				AccountType.SAVINGS,
				BigDecimal.valueOf(0.0),
				LocalDateTime.now(),
				LocalDateTime.now()
		);
		AccountDTO accountIfNotExists = accountClient.createAccountIfNotExists(accountDTO, header);
		if(accountIfNotExists == null) {
			throw new UserNotExistsException("Unable to create account with email " + user.getUserEmail());
		}
		return savedUser;
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
