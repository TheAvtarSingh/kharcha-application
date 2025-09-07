package org.kharcha.app.repositories;

import java.util.List;

import org.kharcha.kharcha.common.types.AccountType;
import org.kharcha.app.entities.Accounts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Accounts, String> {
   List<Accounts> findByUserEmail(String userEmail);
   Accounts findByUserEmailAndAccountType(String userEmail,AccountType accountType);
}
