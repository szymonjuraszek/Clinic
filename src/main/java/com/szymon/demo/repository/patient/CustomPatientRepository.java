package com.szymon.demo.repository.patient;

import org.springframework.web.multipart.MultipartFile;

public interface CustomPatientRepository {

    boolean updateImageProfile(String title, MultipartFile image, Long timestamp);
}
