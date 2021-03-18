package com.szymon.demo.service.file;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class DoctorImageFileHandler implements ImageFileHandler {

    private static final String FILE_PATH_FOR_DOCTOR = "src/main/resources/static/content/images/doctor/";

    @SneakyThrows
    @Override
    public void save(String title, byte[] image, String extension, Long timestamp) {
        File file = new File(FILE_PATH_FOR_DOCTOR + title + "." + extension);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(image);
        }
    }
}
