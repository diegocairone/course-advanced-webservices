package com.cairone.data.collection.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AppMongoRepository<T, I> extends MongoRepository<T, I>, QuerydslPredicateExecutor<T> {

}
