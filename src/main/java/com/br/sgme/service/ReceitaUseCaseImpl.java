package com.br.sgme.service;

import com.br.sgme.controller.despesa.dto.DespesaDto;
import com.br.sgme.controller.receita.dto.ReceitaDto;
import com.br.sgme.enums.FormasPagamento;
import com.br.sgme.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.model.Cliente;
import com.br.sgme.model.Despesa;
import com.br.sgme.model.Receita;
import com.br.sgme.model.usuario.Usuario;
import com.br.sgme.ports.ReceitaUseCase;
import com.br.sgme.repository.ClienteRepository;
import com.br.sgme.repository.ReceitaRepository;
import com.br.sgme.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceitaUseCaseImpl implements ReceitaUseCase {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final ReceitaRepository receitaRepository;

    @Override
    public ResponseEntity<?> save(ReceitaDto receitaDto) {

        Usuario usuario = usuarioRepository.findById(receitaDto.getIdUsuario()).get();
        Cliente cliente = clienteRepository.findById(receitaDto.getIdCliente()).get();

        String observacao = receitaDto.getObservacao();

        if (observacao == null) observacao = "";

        receitaRepository.save(Receita.builder()
                .usuario(usuario)
                .cliente(cliente)
                .valor(receitaDto.getValor())
                .dataVencimento(receitaDto.getDataVencimento())
                .status(receitaDto.getStatus())
                .pagamento(FormasPagamento.valueOf(receitaDto.getFormaPagamento()))
                .observacao(observacao)
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> update(String id, ReceitaDto receitaDto) {
        try {
            Receita receitaSelecionada = receitaRepository.findById(id).get();



            receitaRepository.save(Receita.builder()
                    .id(receitaSelecionada.getId())
                    .usuario(receitaSelecionada.getUsuario())
                    .cliente(receitaSelecionada.getCliente())
                    .valor(receitaDto.getValor())
                    .dataVencimento(receitaDto.getDataVencimento())
                    .status(receitaDto.getStatus())
                    .pagamento(FormasPagamento.valueOf(receitaDto.getFormaPagamento()))
                    .observacao(receitaDto.getObservacao())
                    .build());

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NoSuchElementException exception) {
            throw new RecursoNaoEncontradoException("Despesa nao encontrada");
        }
    }

    @Override
    public List<ReceitaDto> get(String idUsuario) {
        return receitaRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(ReceitaDto::to)
                .collect(Collectors.toList());
    }


    @Override
    public ReceitaDto getById(String id) {
        return receitaRepository.findById(id)
                .stream()
                .map(ReceitaDto::to)
                .findFirst()
                .orElseThrow(()->new RecursoNaoEncontradoException("Receita não encontrada"));
    }

    @Override
    public void delete(String id) {
        if (receitaRepository.findById(id).isEmpty())throw new RecursoNaoEncontradoException("Receita nao encontrada");
        receitaRepository.deleteById(id);

    }
}
