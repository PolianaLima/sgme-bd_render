package com.br.sgme.controller.receita.dto;

import com.br.sgme.model.Despesa;
import com.br.sgme.model.Receita;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDto {

    private String id;

    @JsonProperty("usuario_id")
    private String idUsuario;

    @JsonProperty("cliente_id")
    private String idCliente;


    private Double valor;

    @JsonProperty("data_vencimento")
    private LocalDate dataVencimento;

    private String status;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    private String observacao;

    public static ReceitaDto to(Receita saved){
        return new ReceitaDto(
                saved.getId(),
                saved.getUsuario().getId(),
                saved.getCliente().getId(),
                saved.getValor(),
                saved.getDataVencimento(),
                saved.getStatus(),
                saved.getPagamento().name(),
                saved.getObservacao()
        );
    }


}
