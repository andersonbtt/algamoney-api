package br.com.bitclouds.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bitclouds.algamoney.api.event.RecursoCriadoEvent;
import br.com.bitclouds.algamoney.api.model.Lancamento;
import br.com.bitclouds.algamoney.api.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> buscarLancamentos(){
		List<Lancamento> lancamentos = repository.findAll(); 
		return ResponseEntity.ok(lancamentos);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
		Lancamento lancamento = repository.findOne(codigo);
		if(null==lancamento){
			return(ResponseEntity.notFound().build());
		}else{
			return(ResponseEntity.ok(lancamento));
		}
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@RequestBody @Valid Lancamento entity, HttpServletResponse response){
		Lancamento lancamento = repository.save(entity);
		publisher.publishEvent(new RecursoCriadoEvent(lancamento, response, lancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
	}

}
