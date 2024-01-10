package com.br.sgme.controller.usuario.dto;


import com.br.sgme.model.usuario.UsuarioRole;

public record RegisterDto(String nome, String login, String senha, UsuarioRole role) {
}
