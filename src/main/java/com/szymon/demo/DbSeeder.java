package com.szymon.demo;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {

        doctorRepository.deleteAll();
        patientRepository.deleteAll();

        Doctor doctor1 = new Doctor(
                "Janusz",
                "Kowalski",
                "dendysta",
                "JanuszKowalski@gmail.com",
                "1233",
                123456789
        );
        Doctor doctor2 = new Doctor(
                "Janusz",
                "Tracz",
                "okulista",
                "JanuszTracz@gmail.com",
                "1233",
                345654189
        );
        Doctor doctor3 = new Doctor(
                "Marzena",
                "Kipiel",
                "terapeuta manualny",
                "marzenaKipiel@gmail.com",
                "1233",
                846743166
        );
        Doctor doctor4 = new Doctor(
                "Zofia",
                "Grzyb",
                "Pediatra",
                "ZofiaGrzyb@gmail.com",
                "1233",
                874453321
        );

        doctorRepository.insert(doctor1);
        doctorRepository.insert(doctor2);
        doctorRepository.insert(doctor3);
        doctorRepository.insert(doctor4);

        Patient patient1 = new Patient(
                "Andrzej",
                "Malinowski",
                "andrzejmalinowski@gmail.com",
                "1233",
                456321789
        );
        Patient patient2 = new Patient(
                "Karyna",
                "Wieckiewicz",
                "kainka@gmail.com",
                "1233",
                906754321
        );


        patientRepository.insert(patient1);
        patientRepository.insert(patient2);

    }
}
