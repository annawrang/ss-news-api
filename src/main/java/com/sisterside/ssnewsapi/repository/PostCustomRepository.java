package com.sisterside.ssnewsapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostCustomRepository<T, String> {

    void deletePostLike(String postNumber, String likeNumber);
}
