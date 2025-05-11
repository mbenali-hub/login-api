package com.ben3li.login_api.servicios;

import com.ben3li.login_api.dto.LoginRegistroRequest;
import com.ben3li.login_api.dto.UsuarioDTO;
import com.ben3li.login_api.entidades.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(LoginRegistroRequest loginRegistroRequest);
}
