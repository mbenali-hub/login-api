package com.ben3li.login_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApiApplication implements CommandLineRunner {

   @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    public static void main(String[] args) {
        SpringApplication.run(LoginApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("🔧 spring.datasource.url = " + dbUrl);
        System.out.println("👤 spring.datasource.username = " + dbUser);
        System.out.println("🔐 spring.datasource.password = " + dbPass);  // ⚠️ Solo en dev
        System.out.println("🔑 jwt.secretKey = " + jwtSecret);            // ⚠️ Solo en dev
    }
}
