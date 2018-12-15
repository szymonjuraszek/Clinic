package com.szymon.demo.repository;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {

    default Patient findByName(String name){
        for(Patient patient: findAll()){
            if(patient.getFirstName().equals(name)){
                return patient;
            }
        }

        return null;
    }

    Patient findByEmail(String email);
}
