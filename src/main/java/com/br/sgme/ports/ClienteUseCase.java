package com.br.sgme.ports;

import com.br.sgme.controller.cliente.dto.ClienteDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteUseCase {
    ResponseEntity<?> save (ClienteDto clienteDto);

    ResponseEntity<?> update(String idCliente, ClienteDto clienteDto);

    List<ClienteDto> get(String idUsuario);

    ClienteDto getId (String id);
    ClienteDto getByCpf (String idUsuario, String cpf);

    void delete( String id);
}
