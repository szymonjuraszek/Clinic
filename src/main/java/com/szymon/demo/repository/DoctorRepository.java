package com.szymon.demo.repository;

import com.szymon.demo.collections.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecialization(String specialization);
}
