package com.example.ecommerce.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;

    private int status;

}
