package com.ben3li.login_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accesToken;
    private String refreshToken;
    private long accesTokenExpiraEn;
    private long refreshTokenExpiraEn;
}
