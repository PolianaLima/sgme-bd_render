package com.br.sgme.controller.usuario;

import com.br.sgme.controller.usuario.dto.AuthenticationDto;
import com.br.sgme.controller.usuario.dto.LoginResponseDto;
import com.br.sgme.controller.usuario.dto.RegisterDto;
import com.br.sgme.ports.UsuarioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://sgme-fe-render.onrender.com")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UsuarioUseCase usuarioUseCase;



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated AuthenticationDto data) {
        return usuarioUseCase.login(data);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrer(@RequestBody @Validated RegisterDto data) {
        return usuarioUseCase.save(data);
    }


}
