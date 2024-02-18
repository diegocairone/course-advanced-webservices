package com.cairone.data.docs.repository;

import com.cairone.data.docs.domain.EmployeeCvDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EmployeeCvRepository extends MongoRepository<EmployeeCvDoc, UUID> {

}
