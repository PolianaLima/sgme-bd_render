package com.br.sgme.repository;

import com.br.sgme.model.Cliente;
import com.br.sgme.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, String> {
    @Query("SELECT c FROM fornecedores c WHERE c.usuario.id = :usuarioId")
    List<Fornecedor> findByUsuarioId(@Param("usuarioId") String usuarioId);

    @Query("SELECT c FROM fornecedores c WHERE c.cnpj = :cnpj AND c.usuario.id = :usuarioId")
    Optional<Fornecedor> findByCnpjAndUsuarioId(@Param("cnpj") String cnpj, @Param("usuarioId") String usuarioId);
}
