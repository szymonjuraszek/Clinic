package com.szymon.demo.controller;


import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.exceptions.EmailUserBusyException;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DoctorRepository doctorRepository;



//    @GetMapping("/all")
//    public List<Patient> getAllPatients(){
//        List<Patient> patients = patientRepository.findAll();
//        return patients;
//    }

//    @GetMapping("/{name}")
//    public Patient getPatientByName(@PathVariable String name){
//       return  patientRepository.findByName(name);
//
//    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Patient patient){
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));

        String email = patient.getEmail();
        Patient tmpPatient = patientRepository.findByEmail(email);
        Doctor tmpDoctor = doctorRepository.findByEmail(email);


        if((tmpPatient == null) && (tmpDoctor == null)){
            Patient savedPatient = patientRepository.insert(patient);


        }else {
            new EmailUserBusyException("email-" + email);
        }


    }

    @Secured("ROLE_PATIENT")
    @GetMapping(path="/profile")
    public Patient getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Patient patient = patientRepository.findByEmail(currentPrincipalName);
        return patient;
    }



}
