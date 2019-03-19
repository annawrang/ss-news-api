package com.sisterside.ssnewsapi.repository;

import com.sisterside.ssnewsapi.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface AccountRepository extends MongoRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(UUID userNumber);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndPassword(String email, String password);
}
