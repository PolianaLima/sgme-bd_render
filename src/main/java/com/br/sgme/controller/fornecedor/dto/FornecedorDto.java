package com.br.sgme.controller.fornecedor.dto;

import com.br.sgme.model.Fornecedor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorDto {
    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    private String cnpj;

    private String nome;

    public static FornecedorDto to(Fornecedor saved) {
       return new FornecedorDto( saved.getId(),
                saved.getUsuario().getId(),
                saved.getCnpj(),
                saved.getNome()
       );
    }

}
