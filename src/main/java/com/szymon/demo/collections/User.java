package com.szymon.demo.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "User")
public class User {

    @Id
    private String id;

    private String email;

    private String password;

    private String roles;

    private boolean active;

    public User() {
    }

    public User(String email, String password, String roles, boolean active) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }
}
