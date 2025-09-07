package org.kharcha.app.entities;

import org.kharcha.kharcha.common.types.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "kharcha-accounts")
public class Accounts {
	

	@Id
    private String accountId;

	@NonNull
	private String userId;

    @NonNull
    private String userEmail;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccountType accountType;

    private BigDecimal balance = BigDecimal.ZERO;

    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    

    public Accounts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public AccountType getAccountType() {
		return accountType;
	}


	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public Accounts(String accountId, String userEmail,String userId, AccountType accountType, BigDecimal balance,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.accountId = accountId;
		this.userEmail = userEmail;
		this.userId = userId;
		this.accountType = accountType;
		this.balance = balance;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
    
}
