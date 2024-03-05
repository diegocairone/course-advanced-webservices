package com.cairone.data.collection.repository;

import com.cairone.data.collection.CustomerDoc;
import org.bson.types.ObjectId;

public interface CustomerRepository extends AppMongoRepository<CustomerDoc, ObjectId> {

}
