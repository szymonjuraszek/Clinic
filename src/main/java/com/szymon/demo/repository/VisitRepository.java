package com.szymon.demo.repository;

import com.szymon.demo.collections.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface VisitRepository extends MongoRepository<Visit, String> {
}
