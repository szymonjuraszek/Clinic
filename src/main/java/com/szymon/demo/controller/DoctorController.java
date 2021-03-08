package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.User;
import com.szymon.demo.dto.DoctorDTO;
import com.szymon.demo.dto.PatientDTO;
import com.szymon.demo.exceptions.EmailUserBusyException;
import com.szymon.demo.exceptions.UserNotFoundException;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import com.szymon.demo.repository.UserRepository;
import com.szymon.demo.security.SecurityConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorRepository doctorRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public DoctorController(
            DoctorRepository doctorRepository,
            PasswordEncoder bCryptPasswordEncoder,
            PatientRepository patientRepository,
            UserRepository userRepository,
            ModelMapper modelMapper
    ) {
        this.doctorRepository = doctorRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody DoctorDTO doctorDTO) {

        Doctor doctor = convertToEntity(doctorDTO);

        String email = doctor.getEmail();
        Optional<Doctor> tmpDoctor = doctorRepository.findByEmail(email);
        Optional<Patient> tmpPatient = patientRepository.findByEmail(email);

        if (tmpDoctor.isEmpty() && tmpPatient.isEmpty()) {
            doctor.setRole(SecurityConstants.ROLE_DOCTOR);
            Doctor savedDoctor = doctorRepository.insert(doctor);

            userRepository.insert(new User(
                    doctor.getEmail(),
                    bCryptPasswordEncoder.encode(doctorDTO.getPassword()),
                    SecurityConstants.ROLE_DOCTOR,
                    true
            ));

            URI location = ServletUriComponentsBuilder
                    .fromPath("/patient/{id}")
                    .buildAndExpand(savedDoctor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            throw new EmailUserBusyException("email-" + email);
        }
    }

    @GetMapping(path = "/id/{id}")
    public Doctor getDoctorById(@PathVariable String id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        System.out.println("Wypisuje dane :    " + doctor);

        if (doctor.isPresent()) {
            return doctor.get();
        } else {
            throw new UserNotFoundException("id-" + id);
        }
    }

    @Secured("ROLE_DOCTOR")
    @GetMapping(path = "/profile")
    public Doctor getProfile(Principal principal) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(principal.getName());

        if (doctor.isEmpty()) {
            return null;
        }

        return doctor.get();
    }

    @GetMapping(path = "/specialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

        if (doctors.isEmpty()) {
            throw new UserNotFoundException("specialization -" + specialization);
        } else {
            return doctors;
        }
    }

    @GetMapping(path = "/specializations")
    public List<String> getAllAvailableSpecializations() {
        return Specialization.getEnumsInLowerCase();
    }

    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }

    private DoctorDTO convertToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }
}
