package com.sisterside.ssnewsapi.repository;

import com.sisterside.ssnewsapi.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String>, PostCustomRepository<Post, String> {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findByPostNumber(String postNumber);
}
