package com.szymon.demo.logging;

import javax.servlet.http.HttpServletRequest;

public class LoggingBodyValidator {

    private static final String PATIENT_IMAGE_PROFILE_SERVLET_PATH = "/images/patient";
    private static final String DOCTOR_IMAGE_PROFILE_SERVLET_PATH = "/images/doctor";

    public static boolean ifLogBody(HttpServletRequest request) {
        return !request.getServletPath().contains(PATIENT_IMAGE_PROFILE_SERVLET_PATH) &&
                !request.getServletPath().contains(DOCTOR_IMAGE_PROFILE_SERVLET_PATH);
    }
}
