package com.ben3li.login_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApiApplication implements CommandLineRunner{
    
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

    public static void main(String[] args) {
        SpringApplication.run(LoginApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
         System.out.println("Ruta del Almacen Privado: " + rutaAlmacen);
        System.out.println("Password del Almacen Privado: " + passwordAlmacen);
        System.out.println("Password de la Clave Privada: " + passwordPrivateKey);
        System.out.println("Alias de la Clave Privada: " + aliasPrivateKey);
        System.out.println("Ruta del Almacen Público: " + rutaAlmacenPublico);
        System.out.println("Password del Almacen Público: " + passwordAlmacenPublico);
        System.out.println("Alias de la Clave Pública: " + aliasPublicKey);
    }
}
