package com.br.sgme.ports;

import com.br.sgme.controller.receita.dto.ReceitaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReceitaUseCase {
    ResponseEntity<?> save(ReceitaDto receitaDto);

    ResponseEntity<?> update(String id, ReceitaDto receitaDto);

    List<ReceitaDto> get(String idUsuario);

    ReceitaDto getById(String id);

    void delete(String id);
}
