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
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis")	
//antes: @RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")	//aula 20.14
public class RestauranteUsuarioResponsavelController {	
	//classe 12.17, 19.12, 19.13, 19.21, 19.30, 20.14, 23.27, 23.28, 23.40
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.21
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.40
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro		//aula 23.28
	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {		//aula 19.30
	    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
    .toCollectionModel(restaurante.getResponsaveis())
        .removeLinks();
	
	if (algaSecurity.podeGerenciarCadastroRestaurantes()) {		//aula 23.40
		usuariosModel.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

		usuariosModel.getContent().stream().forEach(usuarioModel -> {
			usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
					restauranteId, usuarioModel.getId(), "desassociar"));
		});
	}
	
	return usuariosModel;
}

@CheckSecurity.Restaurantes.PodeGerenciarCadastro
@DeleteMapping("/{usuarioId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
	cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);		//aula 23.40
	
	return ResponseEntity.noContent().build();
}

@CheckSecurity.Restaurantes.PodeGerenciarCadastro
@PutMapping("/{usuarioId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
	cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);		//aula 23.40
	
	return ResponseEntity.noContent().build();
	}
}
        
/*.....................................................................trecho comentado na aula 23.40
        .add(algaLinks.linkToRestauranteResponsaveis(restauranteId))
        .add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
		usuariosModel.getContent().stream().forEach(usuarioModel -> {
			usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
        restauranteId, usuarioModel.getId(), "desassociar"));
});
return usuariosModel;
}
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro		//aula 23.28
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {		//aula 19.30
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {//19.30
	cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
	 return ResponseEntity.noContent().build();		//aula 19.30
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro		//aula 23.28
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {	//aula 19.30
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {   //19.30
		cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
		  return ResponseEntity.noContent().build();	//aula 19.30
	}
}
/* código alterado na ..........................................................................aula 19.30
//public List<UsuarioModel> listar(@PathVariable Long restauranteId) {		//aula 19.12
public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {	
	Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
			.removeLinks()
            .add(algaLinks.linkToResponsaveisRestaurante(restauranteId));
		//acima: //	aula 19.21, link pResponsaveisRestaurante na classe AlgaLinks
//			.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)	//aula 19.21
//					.listar(restauranteId)).withSelfRel());
}.....................................................................................aula 23.40*/