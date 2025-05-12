package com.ben3li.login_api.seguridad;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ben3li.login_api.entidades.Usuario;
import com.ben3li.login_api.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service //("loginUserDetailsService")
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService{

    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow();
        return new LoginUserDetails(usuario);
    }

}
