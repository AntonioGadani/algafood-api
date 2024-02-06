package com.algaworks.algafood.api.v1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
//antes: @RequestMapping(value = "/usuarios/{usuarioId}/grupos") //aula 20.14
@RequestMapping(path = "/v1/usuarios/{usuarioId}/grupos")	
public class UsuarioGrupoController {	//aulas 12.16, 19.33, 19.35, 23.34, 23.40
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;	
	@Autowired
	private AlgaLinks algaLinks; 	//aula 19.35   
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar		//aula 23.34
	@GetMapping
	public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {	//aula 19.35
	    Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	    
	    CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
	            .removeLinks();
	  
		if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {	//código abaixo: aula 23.40
			gruposModel.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
			
			gruposModel.getContent().forEach(grupoModel -> {
				grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
						usuarioId, grupoModel.getId(), "desassociar"));
			});
		}
		
		return gruposModel;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}

}
/*........................................................Trecho comentado na aula 23.40
 * 	            .add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

	    gruposModel.getContent().forEach(grupoModel -> {
	        grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
	                usuarioId, grupoModel.getId(), "desassociar"));
	    });
	    
	    return gruposModel;
	}    
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar		//aula 23.34
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {//aula 19.35
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {	//aula 19.35
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();		//aula 19.35		
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar		//aula 23.34
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {		//aula 19.35
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {	//aula 19.35
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();		//aula 19.35
	}
}
/*	public List<GrupoModel> listar(@PathVariable Long usuarioId) {	//aula 19.33, alterado na aula 19.35
public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
	Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
//	return grupoModelAssembler.toCollectionModel(usuario.getGrupos());	//aula 19.33
    return grupoModelAssembler.toCollectionModel(usuario.getGrupos())		//aula 19.33
            .removeLinks();
}.........................................................................................aula 23.40*/	