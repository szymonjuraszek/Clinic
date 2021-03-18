package com.szymon.demo.repository.doctor;

import org.springframework.web.multipart.MultipartFile;

public interface CustomDoctorRepository {

    boolean updateImageProfile(String title, MultipartFile image, Long timestamp);
}
