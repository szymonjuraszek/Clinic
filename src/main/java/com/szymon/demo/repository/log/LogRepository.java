package com.szymon.demo.repository.log;

import com.szymon.demo.collections.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {
}
