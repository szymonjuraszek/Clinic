package com.szymon.demo.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "Log")
public class Log {

    @Id
    private String id;

    private String message;

    private LocalDateTime time;

    private UUID uuid;

    public Log(String message) {
        this.time = LocalDateTime.now();
        this.message = message;
    }

    public Log() {

    }
}
