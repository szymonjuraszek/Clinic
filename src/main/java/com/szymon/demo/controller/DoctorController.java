package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.exceptions.EmailUserBusyException;
import com.szymon.demo.exceptions.UserNotFoundException;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody Doctor doctor){
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));

        String email = doctor.getEmail();
        Doctor tmpDoctor = doctorRepository.findByEmail(email);
        Patient tmpPatient = patientRepository.findByEmail(email);

        if((tmpDoctor == null) && (tmpPatient == null)){
            Doctor savedDoctor = doctorRepository.insert(doctor);

            URI location = ServletUriComponentsBuilder
                    .fromPath("/patient/{id}")
                    .buildAndExpand(savedDoctor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }else{
            new EmailUserBusyException("email-" + email);
        }


        return (ResponseEntity<Object>) ResponseEntity.badRequest();
    }

    @GetMapping(path = "/id/{id}")
    public Doctor getDoctorById(@PathVariable String id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        System.out.println("Wypisuje dane :    "+doctor);

        if(doctor.isPresent()){
            return doctor.get();
        }else {
            throw new UserNotFoundException("id-"+ id);

        }
    }



    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Key1","value1");
        httpHeaders.add("Key2","value2");

        return new ResponseEntity<Object> (HttpStatus.CREATED);
    }


    @Secured("ROLE_DOCTOR")
    @GetMapping(path="/profile")
    public Doctor getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Doctor doctor = doctorRepository.findByEmail(currentPrincipalName);
        return doctor;
    }


    @GetMapping(path = "/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization){
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

        if(doctors.isEmpty()){
            throw new UserNotFoundException("specialization -"+ specialization);
        }else {
            return doctors;
        }
    }

}
