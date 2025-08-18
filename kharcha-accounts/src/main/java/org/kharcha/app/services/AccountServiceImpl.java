package org.kharcha.app.services;

import java.util.List;

import org.kharcha.app.entities.AccountType;
import org.kharcha.app.entities.Accounts;
import org.kharcha.app.exceptions.AccountAlreadyExistsException;
import org.kharcha.app.exceptions.AccountNotExistsException;
import org.kharcha.app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Accounts> getAccounts() {
		return accountRepository.findAll();
	}

	@Override
    public List<Accounts> getAccountsByEmail(String userEmail) {
        List<Accounts> accounts = accountRepository.findByUserEmail(userEmail);
        if (accounts.isEmpty()) {
            throw new AccountNotExistsException("No accounts found for email: " + userEmail);
        }
        return accounts;
    }
	
	@Override
    public Accounts updateAccount(Accounts updatedAccount) {
        Accounts existing =accountRepository.findByUserEmailAndAccountType(updatedAccount.getUserEmail(),updatedAccount.getAccountType());
        if (existing == null) {
			throw new AccountNotExistsException( "Account with User ID " + updatedAccount.getUserEmail() + " and "+updatedAccount.getAccountType()+" does not exist.");
		}else {
			 existing.setAccountType(updatedAccount.getAccountType());
             existing.setBalance(updatedAccount.getBalance());
             return accountRepository.save(existing);
		}
      }

    @Override
    public Accounts removeAccount(String userEmail,AccountType accountType) {
       
        	Accounts account =	accountRepository.findByUserEmailAndAccountType(userEmail, accountType);
        	
        	if (account == null) {
				throw new AccountNotExistsException( "Account with User ID " + userEmail + " and "+accountType+" does not exist.");
			}else {
				accountRepository.delete(account);
	        	
	        	return account;
			}
    }

    @Override
    public Accounts createAccount(Accounts account) {
        List<Accounts> existing = accountRepository.findByUserEmail(account.getUserEmail());
        boolean duplicate = existing.stream()
            .anyMatch(a -> a.getAccountType().equals(account.getAccountType()));

        if (duplicate) {
            throw new AccountAlreadyExistsException(
                "Account of type " + account.getAccountType() + " already exists for user " + account.getUserEmail());
        }

        return accountRepository.save(account);
    }

	
}
