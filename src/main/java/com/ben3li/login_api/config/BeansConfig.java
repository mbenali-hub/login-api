package com.ben3li.login_api.config;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ben3li.login_api.seguridad.GestorDeClaves;


@Configuration
public class BeansConfig {

    @Value("${claveArchivoPEM}")
    private String claveArchivoPEM;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth-> auth
            .anyRequest().permitAll()    
        )
        .csrf(csfr -> csfr.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public PrivateKey privateKey() throws Exception{
        return GestorDeClaves.cargarClavePrivada("/claves/private_api_login_key.pem","123qwe");
    }

    @Bean
    public PublicKey publicKey(){
        return GestorDeClaves.cargarClavePublica("/claves/public_api_login_key.pem");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    // @Bean 
    // public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio){
    //     return new LoginUserDetailsService(usuarioRepositorio);
    // }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
