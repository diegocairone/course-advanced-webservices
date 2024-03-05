package com.cairone.data.collection.repository;

import com.cairone.data.collection.CityDoc;
import org.bson.types.ObjectId;

public interface CityRepository extends AppMongoRepository<CityDoc, ObjectId> {

}
