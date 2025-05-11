package com.ben3li.login_api.excepciones;


public class UsuarioExistenteException extends RuntimeException{

    public UsuarioExistenteException(String mensaje){
        super(mensaje);
    }
}
