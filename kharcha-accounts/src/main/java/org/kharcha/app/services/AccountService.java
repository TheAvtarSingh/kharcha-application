package org.kharcha.app.services;

import java.util.List;

import org.kharcha.kharcha.common.types.AccountType;
import org.kharcha.app.entities.Accounts;


public interface AccountService {
public List<Accounts> getAccounts();
List<Accounts> getAccountsByEmail(String userEmail);
Accounts updateAccount(Accounts updatedAccount);
Accounts removeAccount(String accountId,AccountType accountType);
Accounts createAccount(Accounts account);

}
