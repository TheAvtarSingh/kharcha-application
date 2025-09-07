package org.kharcha.app.services;

import java.util.List;

import org.kharcha.app.entities.Transaction;
import org.kharcha.kharcha.common.types.TransactionType;

public interface TransactionService {

    List<Transaction> getTransactions();

    List<Transaction> getTransactionsByEmail(String userEmail);

    Transaction updateTransaction(Transaction updatedTransaction);

    Transaction removeTransaction(String userEmail, TransactionType transactionType);

    Transaction createTransaction(Transaction transaction);
}
