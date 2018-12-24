package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Doctor tmpDoctor = doctorRepository.findByEmail(doctor.getEmail());
        System.out.println(tmpDoctor);
        if(tmpDoctor == null){
            doctorRepository.insert(doctor);
        }else{
            System.out.println("Podany adres email jest juz w uzytku(Doctor)!");
        }


    }

    @GetMapping(path = "/id/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable String id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        System.out.println("Wypisuje dane :    "+doctor);

        if(doctor == null){
            return null;
        }else {
            return  doctor;
        }
    }

    @Secured("ROLE_DOCTOR")
    @GetMapping("/test")
    public String test(){
        return "{\"status\": \"ok\"}";
    }

    @Secured("ROLE_DOCTOR")
    @GetMapping(path="/profile")
    public Doctor getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName + "Lekarz");
        Doctor doctor = doctorRepository.findByEmail(currentPrincipalName);
        return doctor;
    }


    @GetMapping(path = "/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization){
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);
        // System.out.println(doctors);

        return doctors;
    }

}
