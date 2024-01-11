package com.br.sgme.controller.fornecedor;

import com.br.sgme.controller.fornecedor.dto.FornecedorDto;
import com.br.sgme.ports.FornecedorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("https://sgme-fe-render.onrender.com")
@RestController
@RequiredArgsConstructor
@RequestMapping("fornecedores")
public class FornecedorController {
    private final FornecedorUseCase fornecedorUseCase;

    @GetMapping()
    public List<FornecedorDto> getAllFornecedores(@RequestParam UUID idUsuario){
        return fornecedorUseCase.get((idUsuario).toString());
    }

    @GetMapping("/cnpj")
    public  FornecedorDto getFornecedorCnpj(@RequestParam("cnpj") String cnpj, @RequestParam("usuarioId") UUID idUsuario){
        return fornecedorUseCase.getByCnpj(cnpj, (idUsuario).toString());
    }

    @GetMapping("/{id}")
    public FornecedorDto getFornecedorId(@PathVariable UUID id){
        return fornecedorUseCase.getById((id).toString());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated FornecedorDto fornecedorDto){
        return fornecedorUseCase.save(fornecedorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody FornecedorDto fornecedorDto){
        return fornecedorUseCase.update(id, fornecedorDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
         fornecedorUseCase.delete((id).toString());
    }

}
