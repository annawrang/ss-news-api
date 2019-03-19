package com.sisterside.ssnewsapi.repository;

import com.sisterside.ssnewsapi.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findByPostNumber(String postNumber);
}
