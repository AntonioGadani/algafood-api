package com.algaworks.algafood.api.v1.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController		//é um controlador e retornará respostas conforme requisições HTTP
@RequestMapping(path = "/v1/estados")		//antes: @RequestMapping("/estados")	//aula 20.14
public class EstadoController {	//aulas 4.11, 4.32, 5.5, 8.6, 11.20, 19.14, 23.33

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@CheckSecurity.Estados.PodeConsultar	//aula 23.33
	@GetMapping
	public CollectionModel<EstadoModel> listar() {	//aula 19.14
		//return estadoRepository.findAll(); coment: aula 11.20
		List<Estado> todosEstados = estadoRepository.findAll();
		return estadoModelAssembler.toCollectionModel(todosEstados);
	}
	
	@CheckSecurity.Estados.PodeConsultar	//aula 23.33
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		//abaixo: método buscarOuFalhar, em CadastroEstadoService,
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);  // captura o id, se existe estado.Se não, lança Exception
		return estadoModelAssembler.toModel(estado);
	}
	
	@CheckSecurity.Estados.PodeEditar	//aula 23.33
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	//Se, tudo de acordo, em CadastroEstadoService.salvar, Retorna Status 201
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		estado = cadastroEstado.salvar(estado);
		return estadoModelAssembler.toModel(estado);
	}	

	@CheckSecurity.Estados.PodeEditar	//aula 23.33
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId,
			@RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);//para verificação,chama método buscarOuFalhar
		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		
		estadoAtual = cadastroEstado.salvar(estadoAtual);
		
		return estadoModelAssembler.toModel(estadoAtual);
}
	
	@CheckSecurity.Estados.PodeEditar	//aula 23.33
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);	
	}
	
}
