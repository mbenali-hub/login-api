package com.ben3li.login_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ben3li.login_api.dto.UsuarioDTO;
import com.ben3li.login_api.entidades.Usuario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final ModelMapper modelMapper;
    public UsuarioDTO toDto(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
