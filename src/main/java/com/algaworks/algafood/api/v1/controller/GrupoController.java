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

import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/v1/grupos")	//antes: @RequestMapping("/grupos")	//aula 20.14
public class GrupoController {		//aula 12.8, 19.33, 20.14, 23.34

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar		//aula 23.34
	@GetMapping
//	public List<GrupoModel> listar() {	//aula 19.33
	public CollectionModel<GrupoModel> listar(){	//aula 19.33
		List<Grupo> todosGrupos = grupoRepository.findAll();
		return grupoModelAssembler.toCollectionModel(todosGrupos);//Assembler chama coleção GrupoModel p/representação
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar		//aula 23.34
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		return grupoModelAssembler.toModel(grupo);
		/*   Assembler chama recurso unitário de GrupoModel   */
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar		//aula 23.34
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)/*Se, tudo de acordo, 
    						            em CadastroGrupoService.salvar,retorna Status 201  */
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		/*método toDomainObject, abaixo, converte converte grupoInput para grupo.
		  							Feito isso, pode salvar um grupo na entidade Grupo */
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
		grupo = cadastroGrupo.salvar(grupo);
		return grupoModelAssembler.toModel(grupo);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar		//aula 23.34
	@PutMapping("/{grupoId}")/* ver mesmo método atualizado em RestauranteController,aula 11.17    */
	public GrupoModel atualizar(@PathVariable Long grupoId,
			@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
		/* abaixo: método copyToDomainObject, em GrupoInputDisassembler, converte grupoInput 
		    para Grupo, ficando os dados aptos para serem salvos na classe de domínio: Grupo*/
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		return grupoModelAssembler.toModel(grupoAtual);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar		//aula 23.34
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);	
	}
	
}
