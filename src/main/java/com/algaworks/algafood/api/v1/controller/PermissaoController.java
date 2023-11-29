package com.algaworks.algafood.api.v1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/v1/permissoes")	//antes: @RequestMapping(path = "/permissoes")	//aula 20.14
public class PermissaoController{	//aula 19.34, 20.14, 23.34
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.34
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar		//aula 23.34
	@GetMapping
	public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
	    Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
	    CollectionModel<PermissaoModel> permissoesModel 
	        = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
	            .removeLinks()
	            .add(algaLinks.linkToGrupoPermissoes(grupoId))
	            .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
	    
	    permissoesModel.getContent().forEach(permissaoModel -> {
	        permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
	                grupoId, permissaoModel.getId(), "desassociar"));
	    });
	    
	    return permissoesModel;
	}    
/*.........................................................................método listar alterado na aula 19.34
	@GetMapping
	public CollectionModel<PermissaoModel> listar() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();
		return permissaoModelAssembler.toCollectionModel(todasPermissoes);
	}..........................................................................................................*/
}