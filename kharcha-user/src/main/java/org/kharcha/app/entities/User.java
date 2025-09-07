package org.kharcha.app.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "kharcha-users")
public class User {
	
	public User() {
		
	}

	public User(String userEmail,String userPassword) {
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	
	@Id
	private String userId;
	
	@NonNull
	private String userEmail;
	
	@NonNull
	private String userPassword;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	
	

}
