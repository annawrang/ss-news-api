package com.sisterside.ssnewsapi.repository;

import com.sisterside.ssnewsapi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserNumber(String userNumber);

    int countByUserNumber(String userNumber);
}
