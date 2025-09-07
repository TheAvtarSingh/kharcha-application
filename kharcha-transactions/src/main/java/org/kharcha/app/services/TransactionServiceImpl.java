package org.kharcha.app.services;

import java.time.LocalDateTime;
import java.util.List;

import org.kharcha.app.communications.UserClient;
import org.kharcha.app.entities.Transaction;
import org.kharcha.kharcha.common.types.TransactionType;
import org.kharcha.app.exceptions.TransactionNotExistsException;
import org.kharcha.app.exceptions.UserNotExistsException;
import org.kharcha.app.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByEmail(String userEmail) {
        if(userClient.userExists(userEmail)){
            List<Transaction> transactions = transactionRepository.findByUserEmail(userEmail);
            if (transactions.isEmpty()) {
                throw new TransactionNotExistsException("No transactions found for email: " + userEmail);
            }
            return transactions;
        }
        throw new UserNotExistsException("User does not exist: " + userEmail);
    }

    @Override
    public Transaction updateTransaction(Transaction updatedTransaction) {
        Transaction existing = transactionRepository.findByUserEmailAndTransactionType(
                updatedTransaction.getUserEmail(),
                updatedTransaction.getTransactionType()
        );

        if (existing == null) {
            throw new TransactionNotExistsException(
                    "Transaction with User ID " + updatedTransaction.getUserEmail() +
                            " and " + updatedTransaction.getTransactionType() + " does not exist."
            );
        } else {
            existing.setTransactionType(updatedTransaction.getTransactionType());
            existing.setAmount(updatedTransaction.getAmount());
            existing.setDescription(updatedTransaction.getDescription());
            existing.setUpdatedAt(LocalDateTime.now());
            return transactionRepository.save(existing);
        }
    }

    @Override
    public Transaction removeTransaction(String userEmail, TransactionType transactionType) {
        Transaction transaction = transactionRepository.findByUserEmailAndTransactionType(userEmail, transactionType);

        if (transaction == null) {
            throw new TransactionNotExistsException(
                    "Transaction with User ID " + userEmail + " and " + transactionType + " does not exist."
            );
        } else {
            transactionRepository.delete(transaction);
            return transaction;
        }
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        if(userClient.userExists(transaction.getUserEmail())){
            return transactionRepository.save(transaction);
        }
        throw new UserNotExistsException("User does not exist: " + transaction.getUserEmail());
    }
}
