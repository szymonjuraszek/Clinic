package com.szymon.demo.controller;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.User;
import com.szymon.demo.dto.DoctorDTO;
import com.szymon.demo.exceptions.EmailUserBusyException;
import com.szymon.demo.exceptions.UserNotFoundException;
import com.szymon.demo.repository.doctor.DoctorRepository;
import com.szymon.demo.repository.patient.PatientRepository;
import com.szymon.demo.repository.UserRepository;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.szymon.demo.security.SecurityConstants.BASE_URL;

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

            URI location = UriComponentsBuilder
                    .fromPath("/patient/{id}")
                    .buildAndExpand(savedDoctor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            throw new EmailUserBusyException("email-" + email);
        }
    }

    @GetMapping(path = "/id/{id}")
    public DoctorDTO getDoctorById(@PathVariable String id) {

        Optional<Doctor> doctor = doctorRepository.findById(id);

        this.logger.info("Get doctor: " + doctor);

        if (doctor.isPresent()) {
            return convertToDto(doctor.get());
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
    public List<DoctorDTO> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

        if (doctors.isEmpty()) {
            throw new UserNotFoundException("specialization -" + specialization);
        } else {
            return convertListToDto(doctors);
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
        DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);

        if (doctor.getProfileImage() != null) {
            doctorDTO.setImageLocation(
                    BASE_URL +
                            SecurityConstants.DOCTOR_IMAGE_PROFILE_URL +
                            Objects.toString(doctor.getProfileImage().getTimestamp(), "") +
                            doctor.getProfileImage().getTimestamp() +
                            doctor.getEmail() + "." +
                            doctor.getProfileImage().getFormat().toLowerCase());
        }

        return doctorDTO;
    }

    private List<DoctorDTO> convertListToDto(List<Doctor> doctors) {
        List<DoctorDTO> doctorsDTO = new LinkedList<>();

        for (Doctor doctor : doctors) {
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);

            if (doctor.getProfileImage() != null) {
                doctorDTO.setImageLocation(
                        BASE_URL +
                                SecurityConstants.DOCTOR_IMAGE_PROFILE_URL +
                                doctor.getEmail() +
                                "." +
                                doctor.getProfileImage().getFormat().toLowerCase());
//                                "?t=" + Objects.toString(doctor.getProfileImage().getTimestamp(), ""));
            }

            doctorsDTO.add(doctorDTO);
        }

        return doctorsDTO;
    }

    @Secured(SecurityConstants.ROLE_DOCTOR)
    @PutMapping(path = "/profile")
    public DoctorDTO updateProfile(@RequestBody DoctorDTO doctorToUpdateDTO, Principal principal) {

        Doctor doctorToUpdate = convertToEntity(doctorToUpdateDTO);

        Optional<Doctor> currentDoctor = doctorRepository.findByEmail(principal.getName());

        if (currentDoctor.isEmpty()) {
            throw new UserNotFoundException("User hasn't founded: " + principal.getName());
        }

        currentDoctor.get().setFirstName(doctorToUpdate.getFirstName());
        currentDoctor.get().setLastName(doctorToUpdate.getLastName());
        currentDoctor.get().setPhoneNumber(doctorToUpdate.getPhoneNumber());
        currentDoctor.get().setSpecialization(doctorToUpdate.getSpecialization());
        currentDoctor.get().setDegree(doctorToUpdate.getDegree());

        Doctor result = doctorRepository.save(currentDoctor.get());

        return convertToDto(result);
    }
}
