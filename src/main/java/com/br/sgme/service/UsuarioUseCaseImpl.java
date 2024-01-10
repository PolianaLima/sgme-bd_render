package com.br.sgme.service;

import com.br.sgme.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.controller.usuario.dto.RegisterDto;
import com.br.sgme.exceptions.ErrorDetails;
import com.br.sgme.exceptions.LoginInvalidoException;
import com.br.sgme.infra.security.TokenService;
import com.br.sgme.model.usuario.Usuario;
import com.br.sgme.ports.UsuarioUseCase;
import com.br.sgme.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;
import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;

import static com.br.sgme.model.usuario.UsuarioRole.ADMIN;

@Service
@RequiredArgsConstructor
public class UsuarioUseCaseImpl implements UsuarioUseCase {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository usuarioRepository;

    private final TokenService tokenService;
    @Override
    public ResponseEntity<?> save(RegisterDto registerDto) {

        ResponseEntity<ErrorDetails> verificaredUsuario = verificarUsuario(registerDto);
        if (verificaredUsuario != null) return verificaredUsuario;

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.senha());


        Usuario newUsuario = new Usuario(registerDto.nome(), registerDto.login(), encryptedPassword, registerDto.role());

        this.usuarioRepository.save(newUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(AuthenticationDto authenticationDTO) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.senha());
        try {

            var auth = this.authenticationManager.authenticate(usernamePassword);
            var usuario = ((Usuario) auth.getPrincipal());
            var token = tokenService.generateToken(usuario);

            var loginResponseDto = new LoginResponseDto(token, usuario);

            return ResponseEntity.ok(loginResponseDto);
        } catch (BadCredentialsException e) {
            throw new LoginInvalidoException(e.getMessage());
        }


    }


    private ResponseEntity<ErrorDetails> verificarUsuario(RegisterDto data) {
        if (this.usuarioRepository.findByLogin(data.login()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorDetails("Login em uso", LocalDateTime.now(), HttpStatus.CONFLICT.value()));
        }
        return null;
    }
}
