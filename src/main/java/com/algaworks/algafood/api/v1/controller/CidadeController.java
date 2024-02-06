package com.algaworks.algafood.api.v1.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.core.security.CheckSecurity;
//import com.algaworks.algafood.core.web.AlgaMediaTypes;	//aula 20.11
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
//@RequestMapping(value = "/cidades")	//, produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
	@RequestMapping(path = "/v1/cidades")	//aula 20.13,versionamento(URI)
	public class CidadeController {	/*aulas 4.32, 5.5, 8.6, 8.8, 8.10, 9.7, 9.8, 11.20, 19.2, 19.7
										19.8, 19.9, 19.10, 19.11, 20.10, 20.12, 20.13, 23.33   */
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	//@GetMapping(produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)//aula 20.10, versionamento por mediaType
	@CheckSecurity.Cidades.PodeConsultar		//aula 23.33
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)	//aula 20.12, versionamento por URI
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
//@Deprecated	//para depreciação de uma versão. Anotação deve ser inclusa em todos os métodos	
//	@GetMapping(path = "/{cidadeId}", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)	//aula 20.10
	@CheckSecurity.Cidades.PodeConsultar		//aula 23.32
	@GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)//aula 20.12,versionamento(URI)
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		return cidadeModelAssembler.toModel(cidade);
	}
//	@PostMapping(produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)	//aula 20.10
	@CheckSecurity.Cidades.PodeEditar	//aula 23.33
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)	//aula 20.12,versionamento(URI)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade = cadastroCidade.salvar(cidade);
			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);	//aula 19.2
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());	//aula 19.2
			return cidadeModel;	//aula 19.2
		//	return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
//	@PutMapping(path = "/{cidadeId}", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)	//aula 20.10
	@CheckSecurity.Cidades.PodeEditar	//aula 23.33
	@PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)//aula 20.12,versionamento(URI)
	public CidadeModel atualizar(@PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			
			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Cidades.PodeEditar	//aula 23.33
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);	
	}
	
}