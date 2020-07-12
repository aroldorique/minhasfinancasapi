package com.rique.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rique.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>  {

}
