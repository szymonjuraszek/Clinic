package com.szymon.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.logging.Logger;

@SpringBootApplication
public class ZnanyLekarzApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZnanyLekarzApplication.class, args);
    }
}
