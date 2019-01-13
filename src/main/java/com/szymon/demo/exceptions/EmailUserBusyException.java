package com.szymon.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class EmailUserBusyException extends RuntimeException{
    public EmailUserBusyException(String message) {
        super(message);
    }
}
