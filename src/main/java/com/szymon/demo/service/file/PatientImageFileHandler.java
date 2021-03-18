package com.szymon.demo.service.file;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class PatientImageFileHandler implements ImageFileHandler {

    private static final String FILE_PATH_FOR_PATIENT = "src/main/resources/static/images/patient/";

    @SneakyThrows
    @Override
    public void save(String title, byte[] image, String extension, Long timestamp) {
        File file = new File(FILE_PATH_FOR_PATIENT + title + "." + extension);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(image);
        }
    }
}
