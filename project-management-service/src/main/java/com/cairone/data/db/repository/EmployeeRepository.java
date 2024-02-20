package com.cairone.data.db.repository;

import com.cairone.data.db.domain.EmployeeEntity;

import java.util.UUID;

public interface EmployeeRepository extends AppJpaRepository<EmployeeEntity, UUID> {

}
