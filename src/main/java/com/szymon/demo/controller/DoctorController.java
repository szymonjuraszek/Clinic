package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/all")
    public List<Doctor> getAll(){
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors;
    }


    @PostMapping("/sign-up")
    public void signUp(@RequestBody Doctor doctor){
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        doctorRepository.insert(doctor);
        /*System.out.println(doctor.getEmail());
        System.out.println(doctor.getPassword());*/

    }

    @Secured("ROLE_DOCTOR")
    @GetMapping("/test")
    public String test(){
        return "{\"status\": \"ok\"}";
    }


    @GetMapping(path = "/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization){
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

        return doctors;
    }

}
