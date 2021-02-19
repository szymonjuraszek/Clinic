package com.szymon.demo.collections;


import com.szymon.demo.security.SecurityConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Patient")
public class Patient {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private int phoneNumber;

    private List<Visit> visits = new ArrayList<>();

    private String role = SecurityConstants.ROLE_PATIENT;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String email, int phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phone;
    }
}
