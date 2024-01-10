package com.br.sgme.ports;

import com.br.sgme.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.controller.usuario.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface UsuarioUseCase {

    ResponseEntity<?> save (RegisterDto registerDto);

    ResponseEntity<LoginResponseDto>login(AuthenticationDto authenticationDTO);

}
