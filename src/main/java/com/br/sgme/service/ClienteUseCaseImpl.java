package com.br.sgme.service;

import com.br.sgme.controller.cliente.dto.ClienteDto;
import com.br.sgme.exceptions.ErrorDetails;
import com.br.sgme.exceptions.RecursoNaoEncontradoException;
import com.br.sgme.model.Cliente;
import com.br.sgme.model.usuario.Usuario;
import com.br.sgme.ports.ClienteUseCase;
import com.br.sgme.repository.ClienteRepository;
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
public class ClienteUseCaseImpl implements ClienteUseCase {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<?> save(ClienteDto clienteDto) {

        Usuario usuario = usuarioRepository.findById(clienteDto.getIdUsuario()).get();

        ResponseEntity<ErrorDetails> cpfIsPresent = verificaCpf(clienteDto);
        if (cpfIsPresent != null) return cpfIsPresent;

        Cliente cliente = Cliente.builder()
                .usuario(usuario)
                .cpf(clienteDto.getCpf())
                .nome(clienteDto.getNome())
                .dataNascimento(clienteDto.getDataNascimento())
                .telefone(clienteDto.getTelefone())
                .build();

        Cliente saved = clienteRepository.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> update(String idCliente, ClienteDto clienteDto) {
        try {
            Cliente clienteSlecionado = clienteRepository.findById(idCliente).get();

            ResponseEntity<ErrorDetails>cpfIsPresent = verificaCpf(clienteDto);
            if (cpfIsPresent != null && !Objects.equals(clienteDto.getCpf(), clienteSlecionado.getCpf()))
                return cpfIsPresent;

            Cliente cliente = Cliente.builder()
                    .id(clienteSlecionado.getId())
                    .usuario(clienteSlecionado.getUsuario())
                    .cpf(clienteDto.getCpf())
                    .nome(clienteDto.getNome())
                    .dataNascimento(clienteDto.getDataNascimento())
                    .telefone(clienteDto.getTelefone())
                    .build();

            clienteRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NoSuchElementException exception) {
            throw new RecursoNaoEncontradoException("Cliente nao encontrado");
        }
    }

    @Override
    public List<ClienteDto> get(String idUsuario) {
        return clienteRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(ClienteDto::to)
                .collect(Collectors.toList());
    }


    @Override
    public ClienteDto getId(String id) {
        return clienteRepository.findById(id)
                .stream()
                .map(ClienteDto::to).findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
    }

    @Override
    public ClienteDto getByCpf(String cpf, String idUsuario) {
        return clienteRepository.findByCpfAndUsuarioId(cpf, idUsuario)
                .stream()
                .map(ClienteDto::to).findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
    }

    @Override
    public void delete(String id) {
        if (clienteRepository.findById(id).isEmpty()) throw new RecursoNaoEncontradoException("Cliente nao encontrado");
        clienteRepository.deleteById(id);
    }

    private ResponseEntity<ErrorDetails> verificaCpf(ClienteDto data) {
        if (clienteRepository.findByCpfAndUsuarioId(data.getCpf(), data.getIdUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ErrorDetails("Cpf cadastrado", LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value()));
        }
        return null;
    }

}
