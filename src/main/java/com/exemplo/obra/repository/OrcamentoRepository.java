package com.exemplo.obra.repository;

import com.exemplo.obra.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByNomeUsuarioContainingIgnoreCase(String nomeUsuario);
}