package com.ben3li.login_api.config;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
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

    @Value("${keystore.private.path}")
    private String rutaAlmacen;

    @Value("${keystore.private.password}")
    private String passwordAlmacen;

    @Value("${keystore.private.key-password}")
    private String passwordPrivateKey;

    @Value("${keystore.private.key-alias}")
    private String aliasPrivateKey;

    @Value("${keystore.public.path}")
    private String rutaAlmacenPublico;

    @Value("${keystore.public.password}")
    private String passwordAlmacenPublico;

    @Value("${keystore.public.key-alias}")
    private String aliasPublicKey;

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
        return GestorDeClaves.cargarClavePrivada(rutaAlmacen,passwordAlmacen,aliasPrivateKey);
    }

    @Bean
    public PublicKey publicKey() throws Exception{
        return GestorDeClaves.cargarClavePublica(rutaAlmacenPublico, passwordAlmacenPublico, aliasPublicKey);
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
