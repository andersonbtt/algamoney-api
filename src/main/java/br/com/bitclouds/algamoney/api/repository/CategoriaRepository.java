package br.com.bitclouds.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bitclouds.algamoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
