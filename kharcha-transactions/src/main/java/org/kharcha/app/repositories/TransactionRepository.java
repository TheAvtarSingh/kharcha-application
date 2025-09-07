package org.kharcha.app.repositories;

import java.util.List;

import org.kharcha.app.entities.Transaction;
import org.kharcha.kharcha.common.types.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

   List<Transaction> findByUserEmail(String userEmail);

   Transaction findByUserEmailAndTransactionType(String userEmail, TransactionType transactionType);
}
