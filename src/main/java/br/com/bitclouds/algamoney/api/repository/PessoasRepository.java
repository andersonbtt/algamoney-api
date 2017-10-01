package br.com.bitclouds.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bitclouds.algamoney.api.model.Pessoa;

public interface PessoasRepository extends JpaRepository<Pessoa, Long>{

}
