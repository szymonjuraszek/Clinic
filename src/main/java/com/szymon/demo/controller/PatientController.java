package com.szymon.demo.controller;


import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.User;
import com.szymon.demo.dto.PatientDTO;
import com.szymon.demo.exceptions.EmailUserBusyException;
import com.szymon.demo.exceptions.UserNotFoundException;
import com.szymon.demo.repository.UserRepository;
import com.szymon.demo.repository.doctor.DoctorRepository;
import com.szymon.demo.repository.patient.PatientRepository;
import com.szymon.demo.security.SecurityConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

import static com.szymon.demo.security.SecurityConstants.BASE_URL;

@CrossOrigin
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientRepository patientRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final DoctorRepository doctorRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public PatientController(
            PatientRepository patientRepository,
            PasswordEncoder bCryptPasswordEncoder,
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            ModelMapper modelMapper
    ) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody PatientDTO patientDTO) {

        Patient patient = convertToEntity(patientDTO);

        String email = patient.getEmail();
        Optional<Patient> tmpPatient = patientRepository.findByEmail(email);
        Optional<Doctor> tmpDoctor = doctorRepository.findByEmail(email);

        if (tmpPatient.isEmpty() && tmpDoctor.isEmpty()) {
            Patient savedPatient = patientRepository.insert(patient);

            userRepository.insert(new User(
                    patient.getEmail(),
                    bCryptPasswordEncoder.encode(patientDTO.getPassword()),
                    SecurityConstants.ROLE_PATIENT,
                    true
            ));

            URI location = UriComponentsBuilder
                    .fromPath("/patient/{id}")
                    .buildAndExpand(savedPatient.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            throw new EmailUserBusyException("email-" + email);
        }
    }

    @Secured("ROLE_PATIENT")
    @GetMapping(path = "/profile")
    public PatientDTO getProfile(Principal principal) {

        Optional<Patient> patient = patientRepository.findByEmail(principal.getName());

        if (patient.isEmpty()) {
            return null;
        }

        return convertToDto(patient.get());
    }

    private Patient convertToEntity(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    private PatientDTO convertToDto(Patient patient) {
        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);

        if (patient.getProfileImage() != null) {
            patientDTO.setImageLocation(
                    BASE_URL +
                            SecurityConstants.PATIENT_IMAGE_PROFILE_URL +
                            patient.getEmail() +
//                            Objects.toString(patient.getProfileImage().getTimestamp(), "") +
                            "." +
                            patient.getProfileImage().getFormat().toLowerCase());
//                    "?t=" + Objects.toString(patient.getProfileImage().getTimestamp(), ""));
        }

        return patientDTO;
    }

    @Secured("ROLE_PATIENT")
    @PutMapping(path = "/profile")
    public PatientDTO updateProfile(@RequestBody PatientDTO patientToUpdateDTO, Principal principal) {

        Patient patientToUpdate = convertToEntity(patientToUpdateDTO);

        Optional<Patient> currentPatient = patientRepository.findByEmail(principal.getName());

        if (currentPatient.isEmpty()) {
            throw new UserNotFoundException("User hasn't founded: " + principal.getName());
        }

        currentPatient.get().setFirstName(patientToUpdate.getFirstName());
        currentPatient.get().setLastName(patientToUpdate.getLastName());
        currentPatient.get().setPhoneNumber(patientToUpdate.getPhoneNumber());

        Patient result = patientRepository.save(currentPatient.get());

        return convertToDto(result);
    }
}
