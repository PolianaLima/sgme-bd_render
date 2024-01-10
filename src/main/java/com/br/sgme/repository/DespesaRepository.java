package com.br.sgme.repository;

import com.br.sgme.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, String> {
    @Query("SELECT c FROM despesas c WHERE c.usuario.id = :usuarioId")
    List<Despesa> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
