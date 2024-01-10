package com.br.sgme.controller.usuario.dto;

import com.br.sgme.model.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private Usuario usuario;

    public LoginResponseDto(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }


}
