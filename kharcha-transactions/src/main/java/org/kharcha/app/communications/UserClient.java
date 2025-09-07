package org.kharcha.app.communications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "kharcha-user")
public interface UserClient {
    @GetMapping("/api/users/exists/{email}")
    Boolean userExists(@PathVariable("email") String email);
}

@FeignClient(name = "kharcha-accounts")
interface AccountClient {
    @GetMapping("/api/accounts/{id}/exists")
    boolean accountExists(@PathVariable("id") Long id);
}