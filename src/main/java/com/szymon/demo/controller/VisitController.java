package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.Visit;
import com.szymon.demo.exceptions.VisitDoctorBusyException;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/visit")
public class VisitController {

    private final Logger logger = LoggerFactory.getLogger(VisitController.class);

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    public VisitController(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping(path = "/order")
    @Secured({"ROLE_PATIENT"})
    public ResponseEntity<Object> orderVisit(@RequestBody Visit visit, Principal principal) {

        Optional<Doctor> doctor = doctorRepository.findById(visit.getIdDoctor());
        Optional<Patient> patient = patientRepository.findByEmail(principal.getName());

        if (patient.isEmpty() || doctor.isEmpty()) {
            return null;
        }

        visit.setIdPatient(patient.get().getId());

        for (Visit tmpVisit : doctor.get().getVisitList()) {
            if (tmpVisit.getTimeVisit().equals(visit.getTimeVisit())) {
                throw new VisitDoctorBusyException("visit-" + visit);
            }
        }

        doctor.get().getVisitList().add(visit);
        patient.get().getVisits().add(visit);

        doctorRepository.save(doctor.get());
        patientRepository.save(patient.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/sort/{id}")
    @Secured({"ROLE_PATIENT"})
    public List<Visit> sortVisitByTime(@PathVariable String id) {

        Optional<Doctor> tmpDoctor = doctorRepository.findById(id);

        List<Visit> visitList = tmpDoctor.get().getVisitList();

        Collections.sort(visitList);

        return visitList;
    }


}
