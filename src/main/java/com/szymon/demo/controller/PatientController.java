package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/all")
    public List<Patient> getAllPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }

    @GetMapping("/{name}")
    public Patient getPatientByName(@PathVariable String name){
       return  patientRepository.findByName(name);

    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Patient patient){
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        Patient tmpPatient = patientRepository.findByEmail(patient.getEmail());

        if(tmpPatient == null){
            patientRepository.insert(patient);
        }else {
            System.out.println("Podany adres email jest juz w uzytku");
        }

    }

    @Secured("ROLE_PATIENT")
    @GetMapping(path="/profile")
    public Patient getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName + "pacjent");
        Patient patient = patientRepository.findByEmail(currentPrincipalName);
        return patient;
    }



}
