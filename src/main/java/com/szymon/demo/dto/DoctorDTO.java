package com.szymon.demo.dto;

import com.szymon.demo.collections.Visit;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class DoctorDTO {

    @NotBlank
    @NotNull(message = "Field: 'firstName' can't be null")
    private String firstName;

    @NotBlank
    @NotNull(message = "Field: 'lastName' can't be null")
    private String lastName;

    @NotBlank
    @NotNull(message = "Field: 'specialization' can't be null")
    private String specialization;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    @NotNull(message = "Field: 'password' can't be null")
    private String password;

    @Min(value = 100000000, message = "'phoneNumber' must contain 9 digits")
    @Max(value = 999999999, message = "'phoneNumber' must contain 9 digits")
    @NotNull(message = "Field: 'phoneNumber' can't be null")
    private int phoneNumber;

    private List<Visit> visitList = new ArrayList<>();
}
