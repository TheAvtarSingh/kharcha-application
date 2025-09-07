package org.kharcha.kharcha.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.kharcha.kharcha.common.types.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TransactionsDTO {
    private Long transactionId;

    private String userEmail;

    private TransactionType transactionType;

    private BigDecimal amount;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
