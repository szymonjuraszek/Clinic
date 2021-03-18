package com.szymon.demo.repository.patient;

import com.szymon.demo.collections.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String>, CustomPatientRepository {

    Optional<Patient> findByEmail(String email);
}
