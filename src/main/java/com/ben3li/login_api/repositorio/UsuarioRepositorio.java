package com.ben3li.login_api.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben3li.login_api.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {
    Optional<Usuario>findByEmail(String email);
    boolean existsByEmail(String email);
}
