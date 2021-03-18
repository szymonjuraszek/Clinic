package com.szymon.demo.collections;

import com.szymon.demo.security.SecurityConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Doctor")
public class Doctor {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String specialization;

    private String email;

    private int phoneNumber;

    private List<Visit> visitList = new ArrayList<>();

    private String role = SecurityConstants.ROLE_DOCTOR;

    private Photo profileImage;

    private String degree;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String specialization, String email, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
