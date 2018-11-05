package com.sisterside.ssnewsapi.repository;

import com.sisterside.ssnewsapi.domain.Post;
import com.sisterside.ssnewsapi.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<PostDTO, String>, PostCustomRepository<PostDTO, String> {

    Page<PostDTO> findAll(Pageable pageable);

    Optional<PostDTO> findByPostNumber(String postNumber);
}
