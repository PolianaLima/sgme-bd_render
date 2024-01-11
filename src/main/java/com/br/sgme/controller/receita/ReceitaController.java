package com.br.sgme.controller.receita;

import com.br.sgme.controller.despesa.dto.DespesaDto;
import com.br.sgme.controller.receita.dto.ReceitaDto;
import com.br.sgme.ports.ReceitaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("https://sgme-fe-render.onrender.com")
@RestController
@RequiredArgsConstructor
@RequestMapping("/receitas")
public class ReceitaController {
    private final ReceitaUseCase receitaUseCase;

    @GetMapping()
    public List<ReceitaDto> getAllReceitas(@RequestParam UUID idUsuario){
        return receitaUseCase.get((idUsuario).toString());
    }

    @GetMapping("/{id}")
    public ReceitaDto getById(@PathVariable UUID id){
        return receitaUseCase.getById((id).toString());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> save(@RequestBody @Validated ReceitaDto receitaDto){
        return receitaUseCase.save(receitaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable UUID id,@RequestBody ReceitaDto receitaDto){
        return receitaUseCase.update((id).toString(), receitaDto);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable UUID id){
        receitaUseCase.delete((id).toString());
    }
}
