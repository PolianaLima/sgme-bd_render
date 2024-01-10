package com.br.sgme.service;

import com.br.sgme.controller.fornecedor.dto.FornecedorDto;
import com.br.sgme.exceptions.ErrorDetails;
import com.br.sgme.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.model.Fornecedor;
import com.br.sgme.model.usuario.Usuario;
import com.br.sgme.ports.FornecedorUseCase;
import com.br.sgme.repository.FornecedorRepository;
import com.br.sgme.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorUseCaseImpl implements FornecedorUseCase {
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;


    @Override
    public ResponseEntity<?> save(FornecedorDto fornecedorDto) {
        Usuario usuario = usuarioRepository.findById(fornecedorDto.getIdUsuario()).get();

        ResponseEntity<ErrorDetails> cnpfIsPresent = verificaCnpj(fornecedorDto);
        if (cnpfIsPresent != null) return cnpfIsPresent;

        fornecedorRepository.save(Fornecedor.builder()
                .usuario(usuario)
                .cnpj(fornecedorDto.getCnpj())
                .nome(fornecedorDto.getNome())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> update(String id, FornecedorDto fornecedorDto) {

        try {
            Fornecedor fornecedorSelecionado = fornecedorRepository.findById(id).get();
            ResponseEntity<ErrorDetails> cnpfIsPresent = verificaCnpj(fornecedorDto);
            if (cnpfIsPresent != null && !Objects.equals(fornecedorDto.getCnpj(), fornecedorSelecionado.getCnpj()))
                return cnpfIsPresent;

            fornecedorRepository.save(Fornecedor.builder()
                    .id(fornecedorSelecionado.getId())
                    .usuario(fornecedorSelecionado.getUsuario())
                    .cnpj(fornecedorDto.getCnpj())
                    .nome(fornecedorDto.getNome())
                    .build());

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        }catch (NoSuchElementException exception){
            throw new RecursoNaoEncontradoException("Fornecedor não encontrado");
        }
    }

    @Override
    public List <FornecedorDto> get(String idUsuario) {
        return fornecedorRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(FornecedorDto::to)
                .collect(Collectors.toList());
    }

    @Override
    public FornecedorDto getById(String id) {
        return fornecedorRepository.findById(id)
                .stream()
                .map(FornecedorDto::to)
                .findFirst()
                .orElseThrow(()->new RecursoNaoEncontradoException("Fornecedor não encontrado"));
    }

    @Override
    public FornecedorDto getByCnpj(String cnpj, String idUsuario) {
        return fornecedorRepository.findByCnpjAndUsuarioId(cnpj, idUsuario)
                .stream()
                .map(FornecedorDto::to).findFirst()
                .orElseThrow(()->new RecursoNaoEncontradoException("Fornecedor não encontrado"));
    }

    @Override
    public void delete(String id) {
        if(fornecedorRepository.findById(id).isEmpty())throw new RecursoNaoEncontradoException("Fornecedor não encontrado");
        fornecedorRepository.deleteById(id);

    }

    private ResponseEntity<ErrorDetails> verificaCnpj(FornecedorDto data) {
        if (fornecedorRepository.findByCnpjAndUsuarioId(data.getCnpj(), data.getIdUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ErrorDetails("Cnpj já cadastrado", LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value()));
        }

        return null;
    }

}
