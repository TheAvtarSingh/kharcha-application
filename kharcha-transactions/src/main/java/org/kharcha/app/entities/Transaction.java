package org.kharcha.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.kharcha.kharcha.common.types.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "transactions") // maps to MySQL table "transactions"
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;   // Use Long for auto-increment primary key

	@Column(nullable = false)
	private String userEmail;

	@Enumerated(EnumType.STRING)   // Store enum as string in DB (CREDIT, DEBIT, etc.)
	@Column(nullable = false)
	private TransactionType transactionType;

	@Column(nullable = false, precision = 15, scale = 2) // up to 999 trillion, 2 decimal places
	private BigDecimal amount;

	private String description;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Transaction() {
		super();
	}

	public Transaction(Long transactionId, String userEmail, TransactionType transactionType, BigDecimal amount,
					   String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.transactionId = transactionId;
		this.userEmail = userEmail;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

    // JPA lifecycle hooks for audit fields
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
