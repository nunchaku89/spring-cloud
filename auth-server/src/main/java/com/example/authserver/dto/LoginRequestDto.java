package com.example.authserver.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String userPassword;
}
