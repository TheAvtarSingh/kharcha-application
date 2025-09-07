package org.kharcha.app.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kharcha.kharcha.common.dtos.AccountDTO;
import org.kharcha.kharcha.common.types.AccountType;
import org.kharcha.app.entities.Accounts;
import org.kharcha.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/internal/{email}")
    public ResponseEntity<List<Accounts>> getAccountsByEmail(@PathVariable("email") String email
    ,@RequestHeader("X-Internal-Call") String header) {
        if (!"kharcha-user".equals(header)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Accounts> accounts = accountService.getAccountsByEmail(email);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/internal/")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO account,@RequestHeader("X-Internal-Call") String header) {
        if (!"kharcha-user".equals(header)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (account.getUserId() == null || account.getUserEmail() == null || account.getAccountType() == null) {
            throw new IllegalArgumentException("AccountType , UserId and UserEmail must not be null");
        }
        Accounts created = accountService.createAccount(mapToAcc(account));
        AccountDTO accountDTO = mapToDTO(created);
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    @PutMapping("/internal/")
    public ResponseEntity<Accounts> updateAccount(@RequestBody Accounts account,@RequestHeader("X-Internal-Call") String header) {
        if (!"kharcha-user".equals(header)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Accounts updated = accountService.updateAccount(account);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/internal/{userEmail}/{accountType}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userEmail") String userEmail, @PathVariable("accountType") AccountType accountType,@RequestHeader("X-Internal-Call") String header) {
        if (!"kharcha-user".equals(header)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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

    private AccountDTO mapToDTO(Accounts acc) {
        return new AccountDTO(
                acc.getAccountId(),
                acc.getUserId(),
                acc.getUserEmail(),
                acc.getAccountType(),
                acc.getBalance(),
                acc.getCreatedAt(),
                acc.getUpdatedAt()
        );
    }

    private Accounts mapToAcc(AccountDTO acc) {
        return new Accounts(
                acc.getAccountId(),
                acc.getUserId(),
                acc.getUserEmail(),
                acc.getAccountType(),
                acc.getBalance(),
                acc.getCreatedAt(),
                acc.getUpdatedAt()
        );
    }
}
