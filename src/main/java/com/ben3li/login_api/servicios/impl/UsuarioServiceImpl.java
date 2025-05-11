package com.ben3li.login_api.servicios.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ben3li.login_api.dto.LoginRegistroRequest;
import com.ben3li.login_api.dto.UsuarioDTO;
import com.ben3li.login_api.entidades.Usuario;
import com.ben3li.login_api.excepciones.UsuarioExistenteException;
import com.ben3li.login_api.repositorio.UsuarioRepositorio;
import com.ben3li.login_api.servicios.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepositorio usuarioRepositorio;
    @Override
    public Usuario registrarUsuario(LoginRegistroRequest loginRegistroRequest) {
        if(usuarioRepositorio.existsByEmail(loginRegistroRequest.getEmail())){
            throw new UsuarioExistenteException("Ya existe un usuario registrado con este email "+loginRegistroRequest.getEmail());
        }

        Usuario usuario = Usuario.builder()
                                .email(loginRegistroRequest.getEmail())
                                .password(passwordEncoder.encode(loginRegistroRequest.getPassword()))
                                .build();
        return usuarioRepositorio.save(usuario);
    }

}
