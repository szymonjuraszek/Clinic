package com.szymon.demo.exceptions;

public class VisitDoctorBusyException extends RuntimeException{
    public VisitDoctorBusyException(String message) {
        super(message);
    }
}
