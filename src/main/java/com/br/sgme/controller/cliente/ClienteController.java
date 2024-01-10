package com.br.sgme.controller.cliente;

import com.br.sgme.controller.cliente.dto.ClienteDto;
import com.br.sgme.ports.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;


    @GetMapping()
    public List<ClienteDto> getAllClientes(@RequestParam UUID idUsuario){
        return clienteUseCase.get((idUsuario).toString());
    }
   @GetMapping("/cpf")
    public ClienteDto getClienteCPF(@RequestParam String cpf, @RequestParam("usuarioId") UUID idUsuario){
        return clienteUseCase.getByCpf(cpf, (idUsuario).toString());
    }

    @GetMapping("/{id}")
    public ClienteDto getClienteById(@PathVariable UUID id){
        return clienteUseCase.getId((id).toString());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated ClienteDto clienteDto){
        return clienteUseCase.save(clienteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable String id, @RequestBody ClienteDto clienteDto){
        return clienteUseCase.update(id, clienteDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        clienteUseCase.delete((id).toString());
    }

}
