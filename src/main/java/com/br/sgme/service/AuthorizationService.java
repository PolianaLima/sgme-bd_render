package com.br.sgme.service;

import com.br.sgme.exceptions.LoginInvalidoException;
import com.br.sgme.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

    final UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        return usuarioRepository.findByLogin(username)
                .orElseThrow(()->new LoginInvalidoException("Usuário inexistente ou senha inválida"));
    }
}
