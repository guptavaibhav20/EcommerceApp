package com.example.ecommerce.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserInfo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int userId;

    private String username;

    private String password;

    private String roles;

    public UserInfo(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
