package com.ben3li.login_api.servicios;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    UserDetails autenticar(String email, String password);
    String generarToken(UserDetails userDetails,long expiraEn);
    UserDetails validarToken(String token);
    String extraerToken(HttpServletRequest request);
}
