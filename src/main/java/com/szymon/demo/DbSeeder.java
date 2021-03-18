package com.szymon.demo;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.User;
import com.szymon.demo.repository.doctor.DoctorRepository;
import com.szymon.demo.repository.patient.PatientRepository;
import com.szymon.demo.repository.UserRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import static com.szymon.demo.security.SecurityConstants.ROLE_DOCTOR;
import static com.szymon.demo.security.SecurityConstants.ROLE_PATIENT;

@Component
public class DbSeeder implements CommandLineRunner {

    private static final String FILE_PATH = "src/main/resources/static/images/";

    private static final String EXTENSION = ".png";

    private final Logger logger = LoggerFactory.getLogger(DbSeeder.class);

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public DbSeeder(
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            UserRepository userRepository,
            PasswordEncoder encoder
    ) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        doctorRepository.deleteAll();
        patientRepository.deleteAll();
        userRepository.deleteAll();

        Doctor doctor0 = new Doctor(
                "user2",
                "user2",
                "dendysta",
                "a",
                123456789
        );
        Doctor doctor1 = new Doctor(
                "Janusz",
                "Kowalski",
                "dendysta",
                "januszKowalski@gmail.com",
                123456789
        );
        Doctor doctor2 = new Doctor(
                "Janusz",
                "Tracz",
                "okulista",
                "januszTracz@gmail.com",
                345654189
        );
        Doctor doctor3 = new Doctor(
                "Marzena",
                "Kipiel",
                "terapeuta manualny",
                "marzenaKipiel@gmail.com",
                846743166
        );
        Doctor doctor4 = new Doctor(
                "Zofia",
                "Grzyb",
                "pediatra",
                "zofiaGrzyb@gmail.com",
                874453321
        );

        doctorRepository.insert(doctor0);
        doctorRepository.updateImageProfile(doctor0.getEmail(), load(doctor0.getEmail(), "doctor/"), null);
        doctorRepository.insert(doctor1);
        doctorRepository.updateImageProfile(doctor1.getEmail(), load(doctor1.getEmail(), "doctor/"), null);
        doctorRepository.insert(doctor2);
        doctorRepository.updateImageProfile(doctor2.getEmail(), load(doctor2.getEmail(), "doctor/"), null);
        doctorRepository.insert(doctor3);
        doctorRepository.updateImageProfile(doctor3.getEmail(), load(doctor3.getEmail(), "doctor/"), null);
        doctorRepository.insert(doctor4);
        doctorRepository.updateImageProfile(doctor4.getEmail(), load(doctor4.getEmail(), "doctor/"), null);

        Patient patient0 = new Patient(
                "user1",
                "user1",
                "b",
                123456789
        );
        Patient patient1 = new Patient(
                "Andrzej",
                "Malinowski",
                "andrzejmalinowski@gmail.com",
                456321789
        );
        Patient patient2 = new Patient(
                "Koryna",
                "Wieckiewicz",
                "koryna@gmail.com",
                906754321
        );

        patientRepository.insert(patient0);
        patientRepository.updateImageProfile(patient0.getEmail(), load(patient0.getEmail(), "patient/"), null);
        patientRepository.insert(patient1);
        patientRepository.updateImageProfile(patient1.getEmail(), load(patient1.getEmail(), "patient/"), null);
        patientRepository.insert(patient2);
        patientRepository.updateImageProfile(patient2.getEmail(), load(patient2.getEmail(), "patient/"), null);

        logger.info("Add default users into database.");

        userRepository.insert(new User("b", encoder.encode("b"), ROLE_PATIENT, true));
        userRepository.insert(new User("andrzejmalinowski@gmail.com", encoder.encode("1233"), ROLE_PATIENT, true));
        userRepository.insert(new User("kainka@gmail.com", encoder.encode("1233"), ROLE_PATIENT, true));

        logger.info("Add default admins into database.");

        userRepository.insert(new User("a", encoder.encode("a"), ROLE_DOCTOR, true));
        userRepository.insert(new User("JanuszKowalski@gmail.com", encoder.encode("1233"), ROLE_DOCTOR, true));
        userRepository.insert(new User("JanuszTracz@gmail.com", encoder.encode("1233"), ROLE_DOCTOR, true));
        userRepository.insert(new User("marzenaKipiel@gmail.com", encoder.encode("1233"), ROLE_DOCTOR, true));
        userRepository.insert(new User("ZofiaGrzyb@gmail.com", encoder.encode("1233"), ROLE_DOCTOR, false));
    }

    @SneakyThrows
    public MultipartFile load(String title, String userPath) {

        File file = new File(FILE_PATH + userPath + title + EXTENSION);
        FileInputStream input = new FileInputStream(file);

        return new MockMultipartFile("file",
                file.getName(), "image/png", IOUtils.toByteArray(input));
    }
}
