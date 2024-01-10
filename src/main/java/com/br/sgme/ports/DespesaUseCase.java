package com.br.sgme.ports;

import com.br.sgme.controller.despesa.dto.DespesaDto;
import com.br.sgme.controller.fornecedor.dto.FornecedorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DespesaUseCase {

    ResponseEntity<?> save(DespesaDto despesaDto);

    ResponseEntity<?> update(String id, DespesaDto despesaDto);

    List<DespesaDto>get(String id);

    DespesaDto getById(String id);

    void delete(String id);
}
