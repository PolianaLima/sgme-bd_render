package com.br.sgme.controller.cliente.dto;

import com.br.sgme.model.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClienteDto {
    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;


    private String cpf;

    private String nome;

    private String telefone;

    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    public static ClienteDto to(Cliente saved){
        return new ClienteDto(
                saved.getId(),
                saved.getUsuario().getId(),
                saved.getCpf(),
                saved.getNome(),
                saved.getTelefone(),
                saved.getDataNascimento()
                );
    }
}
