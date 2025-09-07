package org.kharcha.kharcha.common.dtos;

import lombok.*;
import org.kharcha.kharcha.common.types.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class AccountDTO {

    private String accountId;

    private String userId;

    private String userEmail;

    private AccountType accountType;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AccountDTO(){}
    public AccountDTO( String accountId ,String userId, String userEmail, AccountType accountType, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}