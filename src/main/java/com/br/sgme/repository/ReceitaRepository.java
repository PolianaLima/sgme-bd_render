package com.br.sgme.repository;

import com.br.sgme.model.Despesa;
import com.br.sgme.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, String> {
    @Query("SELECT c FROM receitas c WHERE c.usuario.id = :usuarioId")
    List<Receita> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
