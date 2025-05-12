package com.ben3li.login_api.seguridad;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class GestorDeClaves {

    public static PrivateKey cargarClavePrivada(String pemFilePath, String password) throws Exception {
        // 1. Leer el archivo PEM
        try (InputStream fis = GestorDeClaves.class.getClassLoader().getResourceAsStream(pemFilePath)) {
            if (fis == null) {
                throw new RuntimeException("Archivo no encontrado: " + pemFilePath);
            }
            byte[] pemBytes = fis.readAllBytes();

            // 2. Procesar la clave encriptada
            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(pemBytes);
            
            // 3. Usar la contraseña para desencriptar
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory skf = SecretKeyFactory.getInstance(
                encryptedPrivateKeyInfo.getAlgName()
            );
            
            PKCS8EncodedKeySpec keySpec = encryptedPrivateKeyInfo.getKeySpec(
                skf.generateSecret(pbeKeySpec)
            );
            
            // 4. Generar la clave privada
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        }
    }


    public static PublicKey cargarClavePublica(String ruta){
        PublicKey clavePublica = null;
        try(InputStream fis = GestorDeClaves.class.getClassLoader().getResourceAsStream(ruta)){
            byte[] claveEnBytes = fis.readAllBytes();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(claveEnBytes, "RSA");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            clavePublica = keyFactory.generatePublic(keySpec);
        }catch(Exception e){
            e.printStackTrace();
        }
        return clavePublica;
    }
}
