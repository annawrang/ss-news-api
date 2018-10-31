package com.sisterside.ssnewsapi.repository;

import com.sisterside.ssnewsapi.domain.Post;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class PostCustomRepositoryImpl implements PostCustomRepository<Post, String>{

    private MongoOperations mongoOperations;

    public PostCustomRepositoryImpl(MongoTemplate mongoOperations){
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void deletePostLike(String postNumber, String likeNumber) {
//        Aggregation agg = newAggregation(
//                match(Criteria.where("post.postNumber").is(postNumber)),
//                unwind("likes");
//
//        AggregationResults<ModelSales> entity = mongoOperation.aggregate(agg, SaleEntity.class, ModelSales.class);

    }
}
