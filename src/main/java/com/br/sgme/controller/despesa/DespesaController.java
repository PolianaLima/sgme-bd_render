package com.br.sgme.controller.despesa;

import com.br.sgme.controller.despesa.dto.DespesaDto;
import com.br.sgme.ports.DespesaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("https://sgme-front-end-vf-zx6w.vercel.app")
@RestController
@RequiredArgsConstructor
@RequestMapping("/despesas")
public class DespesaController {
    private final DespesaUseCase despesaUseCase;

    @GetMapping()
    public List<DespesaDto>getAllDespesas(@RequestParam UUID idUsuario){
        return despesaUseCase.get((idUsuario).toString());
    }

    @GetMapping("/{id}")
    public DespesaDto getById(@PathVariable UUID id){
        return despesaUseCase.getById((id).toString());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated DespesaDto despesaDto){
        return despesaUseCase.save(despesaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable UUID id,@RequestBody DespesaDto despesaDto){
        return despesaUseCase.update((id).toString(), despesaDto);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable UUID id){
        despesaUseCase.delete((id).toString());
    }
}
