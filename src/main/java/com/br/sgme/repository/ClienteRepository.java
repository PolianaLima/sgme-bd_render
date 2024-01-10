package com.br.sgme.repository;

import com.br.sgme.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM clientes c WHERE c.usuario.id = :usuarioId")
    List<Cliente> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM clientes c WHERE c.cpf = :cpf AND c.usuario.id = :usuarioId")
    Optional<Cliente> findByCpfAndUsuarioId(@Param("cpf") String cpf, @Param("usuarioId") String usuarioId);

}
