package com.ben3li.login_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ben3li.login_api.repositorio.UsuarioRepositorio;
import com.ben3li.login_api.seguridad.LoginUserDetailsService;

@Configuration
public class BeansConfig {
     @Bean
    public PasswordEncoder passwordEncoder(){
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

     @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean 
    public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio){
        return new LoginUserDetailsService(usuarioRepositorio);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
