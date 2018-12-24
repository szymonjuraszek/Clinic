package com.szymon.demo.repository;

import com.szymon.demo.collections.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {

    Doctor findByEmail(String email);
    List<Doctor> findBySpecialization(String specialization);



}
