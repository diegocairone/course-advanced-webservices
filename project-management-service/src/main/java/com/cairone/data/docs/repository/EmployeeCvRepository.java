package com.cairone.data.docs.repository;

import com.cairone.data.docs.domain.EmployeeCvDoc;

import java.util.UUID;

public interface EmployeeCvRepository extends AppMongoRepository<EmployeeCvDoc, UUID> {

}
