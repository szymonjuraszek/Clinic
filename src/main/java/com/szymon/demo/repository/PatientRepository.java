package com.szymon.demo.repository;

import com.szymon.demo.collections.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {

    default Patient findByName(String name){
        for(Patient patient: findAll()){
            if(patient.getFirstName().equals(name)){
                return patient;
            }
        }

        return null;
    }

    Optional<Patient> findByEmail(String email);
}
