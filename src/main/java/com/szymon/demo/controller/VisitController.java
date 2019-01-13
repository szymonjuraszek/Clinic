package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.Visit;
import com.szymon.demo.exceptions.VisitDoctorBusyException;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/visit")
public class VisitController {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @PostMapping(path = "/order")
    @Secured({"ROLE_PATIENT"})
    public ResponseEntity<Object> orderVisit(@RequestBody Visit visit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String patientEmail = authentication.getName();
        System.out.println("Jestem w funkcji order");

        // System.out.println(visit);
        // find Doctor and Patient
        Optional<Doctor> doctor = doctorRepository.findById(visit.getIdDoctor());
        Patient patient = patientRepository.findByEmail(patientEmail);

//        System.out.println(doctor);
//        System.out.println(patient.getVisits());


        visit.setIdPatient(patient.getId());


        for (Visit tmpVisit: doctor.get().getVisitList()) {
            if(tmpVisit.getTimeVisit().equals(visit.getTimeVisit())) {
                throw new VisitDoctorBusyException("visit-" + visit);
            }
        }

        doctor.get().getVisitList().add(visit);
        patient.getVisits().add(visit);


        doctorRepository.save(doctor.get());
        patientRepository.save(patient);

        System.out.println(doctor.get().getVisitList());
        System.out.println(patient.getVisits());

        return new ResponseEntity<> (HttpStatus.CREATED);
    }

    @GetMapping(path="/sort/{id}")
    @Secured({"ROLE_PATIENT"})
    public List<Visit> sortVisitByTime(@PathVariable String id) {

        Optional<Doctor> tmpDoctor = doctorRepository.findById(id);

        List<Visit> visitList = tmpDoctor.get().getVisitList();

        Collections.sort(visitList);

        return visitList;
    }


}
