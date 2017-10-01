package br.com.bitclouds.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bitclouds.algamoney.api.model.Pessoa;
import br.com.bitclouds.algamoney.api.repository.PessoasRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoasResource {
	
	@Autowired
	private PessoasRepository pessoasRepository; 
	
	@GetMapping
	public ResponseEntity<?> buscarPessoas(){
		List<Pessoa> pessoas = pessoasRepository.findAll();
		return ResponseEntity.ok(pessoas);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa entity, HttpServletResponse response){
		Pessoa pessoa = pessoasRepository.save(entity);
		
		URI uri = ServletUriComponentsBuilder
						.fromCurrentRequestUri()
						.path("/{codigo}")
						.buildAndExpand(pessoa.getCodigo())
						.toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(pessoa);
	}

}
