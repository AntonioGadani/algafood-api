package com.algaworks.algafood.api.v1.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j	//aula 21.1, para configurar logs
@RestController	//é um controlador e retornará respostas conforme requisições HTTP
//@RequestMapping(value = "/cozinhas")
@RequestMapping(value = "/v1/cozinhas")		//aula 20.14
public class CozinhaController {	/*4.10, 4.14, 4.16, 4.20, 4.21, 4.23, 4.25, 4.26,4.27, 5.4, 8,4, 8.5
......................................11.20, 13.8, 19.15, 20.14, 23.21 */
	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler; //aula 19.15
	
	//@PreAuthorize("isAuthenticated()")	//aula 23.21(Usuário deve estar autenticado
	@CheckSecurity.Cozinhas.PodeConsultar		//aula 23.23
	@GetMapping
	//public List<CozinhaModel> listar() {   //aula 13.8    (paginação)
	/*public Paged<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) 
			acima: aula 13.8(paginação)    */
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		log.info("Consultando cozinhas com páginas de {} registros...", pageable.getPageSize()); /*aula 21.1
		{} é uma place holder que pega o parâmetro pageable.getPageSize()*/
		/*abaixo: aula 21.02, Logs, simulando erro
		if (true) {												
			throw new RuntimeException("Teste de exception");
		}	.....................................................aula 21.02.......*/
		//acima, aula 19.15(hipermedia com paginação
		//List<Cozinha>todasCozinhas = cozinhaRepository.findAll();  coment: aula 13.8
		Page<Cozinha>cozinhasPage = cozinhaRepository.findAll(pageable);
		//return cozinhaModelAssembler.toCollectionModel(todasCozinhas);   coment: aula 13.8
		/*abaixo, instancia um PageImpl, passa o conteúdo(cozinhasModel),um paginador e total de elementos
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler
				.toCollectionModel(cozinhasPage.getContent());
		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel,pageable, 
				cozinhasPage.getTotalElements());
		return cozinhasModelPage;
	}..........trecho substituído na aula 19.15................................................*/
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler 
				.toModel(cozinhasPage, cozinhaModelAssembler);   // aula 19.15
		return cozinhasPagedModel;
	}
	//@PreAuthorize("isAuthenticated()")	//aula 23.21(Usuário deve estar autenticado
	@CheckSecurity.Cozinhas.PodeConsultar		//aula 23.23
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		//return cadastroCozinha.buscarOuFalhar(cozinhaId);	//coment: aula 11.20
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
				return cozinhaModelAssembler.toModel(cozinha);		
	}
	
	//@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")	//aula 23.21(Usuário deve estar autorizado
	@CheckSecurity.Cozinhas.PodeEditar		//aula 23.23
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cadastroCozinha.salvar(cozinha);
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	//@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")	//aula 23.21(Usuário deve estar autorizado
	@CheckSecurity.Cozinhas.PodeEditar		//aula 23.23
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInput cozinhaInput) {
	//abaixo, método buscar ou falhar(cadastroCozinhaService) encontra uma cozinha ou lança uma exception		
	Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
	cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
	cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
	return cozinhaModelAssembler.toModel(cozinhaAtual);
}
	
	//@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")	//aula 23.21(Usuário deve estar autorizado
	@CheckSecurity.Cozinhas.PodeEditar		//aula 23.23
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	
}