package br.com.bitclouds.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.bitclouds.algamoney.api.model.Pessoa;
import br.com.bitclouds.algamoney.api.repository.PessoasRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoasRepository repository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa){
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repository.save(pessoaSalva);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
	}

	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = repository.findOne(codigo);
		if(null==pessoaSalva){
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
	
}
