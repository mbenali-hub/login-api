package com.ben3li.login_api.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.login_api.dto.LoginRegistroRequest;
import com.ben3li.login_api.dto.AuthResponse;
import com.ben3li.login_api.dto.UsuarioDTO;
import com.ben3li.login_api.entidades.Usuario;
import com.ben3li.login_api.mapper.UsuarioMapper;
import com.ben3li.login_api.servicios.AuthenticationService;
import com.ben3li.login_api.servicios.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    private long accesTokenExpiraEn=900000;
    private long refreshTokenExpiraEn=604800000;
    
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> crearUsuario(
        @RequestBody LoginRegistroRequest loginRegistroRequest
    ){
        Usuario usuario = usuarioService.registrarUsuario(loginRegistroRequest);
        return new ResponseEntity<>(usuarioMapper.toDto(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody LoginRegistroRequest loginRegistroRequest
    ){
        UserDetails userDetails = authenticationService.autenticar(loginRegistroRequest.getEmail(), loginRegistroRequest.getPassword());
        String tokenAcceso = authenticationService.generarToken(userDetails, accesTokenExpiraEn);
        String refreshToken = authenticationService.generarToken(userDetails, refreshTokenExpiraEn);
        AuthResponse authResponse = new AuthResponse(tokenAcceso, refreshToken, accesTokenExpiraEn, refreshTokenExpiraEn);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
        HttpServletRequest request
    ){ 
        String refreshToken = authenticationService.extraerToken(request);
        UserDetails userDetails = authenticationService.validarToken(refreshToken);
        String tokenAcceso = authenticationService.generarToken(userDetails, accesTokenExpiraEn);

        return new ResponseEntity<>(new AuthResponse(tokenAcceso,null, accesTokenExpiraEn,0), HttpStatus.OK);
    }
}
