package org.kharcha.app.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kharcha.app.entities.AccountType;
import org.kharcha.app.entities.Accounts;
import org.kharcha.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mongodb.lang.NonNull;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/ping")
    public ResponseEntity<Object> serverStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("service",applicationName );
        map.put("status", "Server is Up!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Accounts>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Accounts>> getAccountsByEmail(@PathVariable("email") String email) {
        List<Accounts> accounts = accountService.getAccountsByEmail(email);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Accounts> createAccount(@RequestBody Accounts account) {
        if (account.getAccountType() == null) {
            throw new IllegalArgumentException("AccountType must not be null");
        }

        Accounts created = accountService.createAccount(account);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Accounts> updateAccount(@RequestBody Accounts account) {
        Accounts updated = accountService.updateAccount(account);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userEmail}/{accountType}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userEmail") String userEmail, @PathVariable("accountType") AccountType accountType) {
    	if(userEmail.equalsIgnoreCase("") || accountType == null) {
    		 Map<String, Object> map = new HashMap<>();
    	        map.put("timestamp", LocalDateTime.now());
    	        map.put("service",applicationName );
    	        map.put("exception", "Missing userEmail or Account Type");
            return ResponseEntity.badRequest().body(map);
    	}
        Accounts deleted = accountService.removeAccount(userEmail,accountType);
        return ResponseEntity.ok(deleted);
    }
}
