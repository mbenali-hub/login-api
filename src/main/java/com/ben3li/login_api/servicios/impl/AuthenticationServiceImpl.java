package com.ben3li.login_api.servicios.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    private final LoginUserDetailsService loginUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public UserDetails autenticar(String email, String password) {
       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password)
       );
       return loginUserDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generarToken(UserDetails userDetails, long expiraEn) {
        Map<String,Object> claims = new HashMap<>();
        
        return Jwts.builder()
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+expiraEn))
                    .setClaims(claims)
                    .signWith(getClaveCodificadora(secretKey),SignatureAlgorithm.HS256)
                    .setSubject(userDetails.getUsername())
                    .compact();
    }

    @Override
    public UserDetails validarToken(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getClaveCodificadora(secretKey))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return loginUserDetailsService.loadUserByUsername(claims.getSubject());
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
