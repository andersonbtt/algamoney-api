package br.com.bitclouds.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bitclouds.algamoney.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
