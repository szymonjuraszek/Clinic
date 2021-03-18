package com.szymon.demo.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 300_300_000; // 5 minutes
    public static final long ADDITIONAL_TIME_TO_REFRESH = 300_420_000; // 7 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String LOGIN_URL = "/login";
    public static final String AUTHORITY_CLAIMS_NAME = "authorities";
    public static final String TIME_TO_REFRESH_CLAIM_NAME = "timeToRefresh";
    public static final String CAN_REFRESH_CLAIM_NAME = "canRefresh";
    public static final String PATH_TO_REFRESH_TOKEN = "/refreshToken";
    public static final String REQUEST_ATTRIBUTE_CLAIMS_NAME = "claims";
    public static final String REQUEST_ATTRIBUTE_USER_NAME = "user";
    public static final String SIGN_UP_URL = "/doctor/sign-up";
    public static final String SIGN_UP_URL_PATIENT = "/patient/sign-up";
    public static final String ROLE_PATIENT = "ROLE_PATIENT";
    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    public static final String PATIENT_IMAGE_PROFILE_URL = "/images/patient/";
    public static final String DOCTOR_IMAGE_PROFILE_URL = "/images/doctor/";
    public static final String BASE_URL = "http://localhost:8080";
}
