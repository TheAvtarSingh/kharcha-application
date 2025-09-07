package org.kharcha.app.communications;

import org.kharcha.kharcha.common.dtos.AccountDTO;
import org.kharcha.kharcha.common.types.AccountType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "kharcha-account")
public interface AccountClient {
    @PostMapping("api/accounts/internal/")
    AccountDTO createAccountIfNotExists(@RequestBody AccountDTO account, @RequestHeader("X-Internal-Call") String header);

    @GetMapping("api/accounts/internal/{emailId}")
    List<AccountDTO> getAllAcountsLinkedWithEmail(@PathVariable("emailId") String emailId, @RequestHeader("X-Internal-Call") String header);

    @DeleteMapping("api/accounts/internal/{userEmail}/{accountType}")
    void deleteAcountsLinkedWithEmail(@PathVariable("userEmail") String userEmail, @PathVariable("accountType") AccountType accountType, @RequestHeader("X-Internal-Call") String header);
}
