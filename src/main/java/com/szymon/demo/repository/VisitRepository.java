package com.szymon.demo.repository;

import com.szymon.demo.collections.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit, String> {
}
