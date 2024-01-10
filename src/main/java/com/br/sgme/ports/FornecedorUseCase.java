package com.br.sgme.ports;

import com.br.sgme.controller.fornecedor.dto.FornecedorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FornecedorUseCase {
    ResponseEntity<?> save(FornecedorDto fornecedorDto);
    ResponseEntity<?>update(String id, FornecedorDto fornecedorDto);

    List<FornecedorDto>get(String idUsuario);

    FornecedorDto getById(String id);

    FornecedorDto getByCnpj(String idUsuario, String cnpj);
    void delete(String id);
}
