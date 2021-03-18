package com.szymon.demo.controller;

import com.szymon.demo.repository.doctor.DoctorRepository;
import com.szymon.demo.repository.patient.PatientRepository;
import com.szymon.demo.security.SecurityConstants;
import com.szymon.demo.service.file.ImageFileHandler;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Date;

@RestController
@CrossOrigin
public class PhotoController {

    private final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    private final ImageFileHandler doctorImageFileHandler;

    private final ImageFileHandler patientImageFileHandler;

    public PhotoController(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            @Qualifier("doctorImageFileHandler") ImageFileHandler doctorImageFileHandler,
            @Qualifier("patientImageFileHandler") ImageFileHandler patientImageFileHandler
    ) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.doctorImageFileHandler = doctorImageFileHandler;
        this.patientImageFileHandler = patientImageFileHandler;
    }

    @SneakyThrows
    @Secured({SecurityConstants.ROLE_PATIENT, SecurityConstants.ROLE_DOCTOR})
    @PostMapping("/photo")
    public ResponseEntity<Object> addPhoto(@RequestParam("image") MultipartFile image, Principal principal) {

        if (image.isEmpty()) {
            logger.error("Image object is null.");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Add profile image " + image.getName() + "  by: " + principal.getName());

        String path = "";

        Long timestamp = new Date().getTime();

        if (patientRepository.updateImageProfile(principal.getName(), image, timestamp)) {
            patientImageFileHandler.save(principal.getName(), image.getBytes(), FilenameUtils.getExtension(image.getOriginalFilename().toLowerCase()), timestamp);
            path = "/images/patient/" +
                    principal.getName() +
                    "." + FilenameUtils.getExtension(image.getOriginalFilename().toLowerCase()
//                    "?t=" + timestamp
            );
        } else if (doctorRepository.updateImageProfile(principal.getName(), image, timestamp)) {
            doctorImageFileHandler.save(principal.getName(), image.getBytes(), FilenameUtils.getExtension(image.getOriginalFilename().toLowerCase()), timestamp);
            path = "/images/doctor/" +
                    principal.getName() +
                    "." + FilenameUtils.getExtension(image.getOriginalFilename().toLowerCase()
//                    "?t=" + timestamp
            );
        } else {
            return ResponseEntity.notFound().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path)
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
