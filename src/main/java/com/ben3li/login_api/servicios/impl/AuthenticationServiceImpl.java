package com.ben3li.login_api.servicios.impl;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ben3li.login_api.seguridad.GestorDeClaves;
import com.ben3li.login_api.seguridad.LoginUserDetailsService;
import com.ben3li.login_api.servicios.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    //@Qualifier("loginUserDetailsService")
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private final PrivateKey clavePrivada;
    private final PublicKey clavePublica;

    @Override
    public UserDetails autenticar(String email, String password) {
       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password)
       );
       return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generarToken(UserDetails userDetails, long expiraEn){
        Map<String,Object> claims = new HashMap<>();
        
        return Jwts.builder()
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setClaims(claims)
                    .signWith(clavePrivada, SignatureAlgorithm.RS256)
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis()+expiraEn))
                    .compact();
    }

    @Override
    public UserDetails validarToken(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(clavePublica)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return userDetailsService.loadUserByUsername(claims.getSubject());
    }

    private Key getClaveCodificadora(String secretKey){
        byte[] claveEnBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(claveEnBytes);
    }

    @Override
    public String extraerToken(HttpServletRequest request) {
        String token="";
        String bearer= request.getHeader("Authorization");

        if(bearer!=null && bearer.startsWith("Bearer ")){
            token=bearer.substring(7);
        }
        return token;
    }


}
