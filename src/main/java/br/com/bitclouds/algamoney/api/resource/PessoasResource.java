package br.com.bitclouds.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bitclouds.algamoney.api.event.RecursoCriadoEvent;
import br.com.bitclouds.algamoney.api.model.Pessoa;
import br.com.bitclouds.algamoney.api.repository.PessoasRepository;
import br.com.bitclouds.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoasResource {
	
	@Autowired
	private PessoasRepository pessoasRepository; 
	
	@Autowired
	private PessoaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> buscarPessoas(){
		List<Pessoa> pessoas = pessoasRepository.findAll();
		return ResponseEntity.ok(pessoas);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa entity, HttpServletResponse response){
		Pessoa pessoa = pessoasRepository.save(entity);
		publisher.publishEvent(new RecursoCriadoEvent(pessoa, response, pessoa.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo){
		Pessoa pessoa = pessoasRepository.findOne(codigo);
		if(null==pessoa){
			return(ResponseEntity.notFound().build());
		}else{
			return(ResponseEntity.ok(pessoa));
		}
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo){
		pessoasRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = service.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){
		service.atualizarPropriedadeAtivo(codigo, ativo);
	}

}
