package com.cairone.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AppDataRepository<T, I> extends JpaRepository<T, I>, QuerydslPredicateExecutor<T> {
}
