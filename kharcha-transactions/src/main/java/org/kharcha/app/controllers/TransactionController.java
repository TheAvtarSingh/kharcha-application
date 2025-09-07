package org.kharcha.app.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kharcha.app.entities.Transaction;
import org.kharcha.kharcha.common.types.TransactionType;
import org.kharcha.app.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/ping")
    public ResponseEntity<Object> serverStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("service", applicationName);
        map.put("status", "Server is Up!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(transactionService.getTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Transaction>> getTransactionsByEmail(@PathVariable("email") String email) {
        List<Transaction> transactions = transactionService.getTransactionsByEmail(email);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws Exception {
        if (transaction.getTransactionType() == null && transaction.getAmount() == null && transaction.getUserEmail()==null) {
            throw new Exception("Transaction Data must not be null");
        }

        Transaction created = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        if (transaction.getTransactionType() == null && transaction.getAmount() == null && transaction.getUserEmail()==null) {
            throw new IllegalArgumentException("Transaction Data must not be null");
        }
        Transaction updated = transactionService.updateTransaction(transaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userEmail}/{transactionType}")
    public ResponseEntity<?> deleteTransaction(
            @PathVariable("userEmail") String userEmail,
            @PathVariable("transactionType") TransactionType transactionType) {

        if (userEmail.equalsIgnoreCase("") || transactionType == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("timestamp", LocalDateTime.now());
            map.put("service", applicationName);
            map.put("exception", "Missing userEmail or Transaction Type");
            return ResponseEntity.badRequest().body(map);
        }

        Transaction deleted = transactionService.removeTransaction(userEmail, transactionType);
        return ResponseEntity.ok(deleted);
    }
}
