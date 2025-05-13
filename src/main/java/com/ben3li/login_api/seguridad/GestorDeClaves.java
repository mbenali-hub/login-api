package com.ben3li.login_api.seguridad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class GestorDeClaves {

    public static PrivateKey cargarClavePrivada(String ruta, String password, String aliasClavePrivada) throws Exception {

        KeyStore keyStore = cargarAlmacen(ruta, password);
        return obtenerPrivateKeyDelAlmacen(keyStore, password, aliasClavePrivada);
    }

    public static PublicKey cargarClavePublica(String rutaAlmacenPublico, String password, String aliasCalvePublica) throws Exception{
       KeyStore keyStore = cargarAlmacen(rutaAlmacenPublico, password);

       PublicKey publicKey = keyStore.getCertificate(aliasCalvePublica).getPublicKey();
       return publicKey;
    }

    private static PrivateKey obtenerPrivateKeyDelAlmacen(KeyStore keyStore, String password, String aliasClavePrivada){
        PrivateKey privateKey=null;

        try {
            privateKey = (PrivateKey) keyStore.getKey(aliasClavePrivada, password.toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return privateKey;
    }

    private static KeyStore cargarAlmacen(String ruta, String password){
        KeyStore keyStore = null;

        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(ruta), password.toCharArray());
        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return keyStore;
    }

}
