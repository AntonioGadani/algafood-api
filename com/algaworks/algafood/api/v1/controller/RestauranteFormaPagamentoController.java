package com.algaworks.algafood.api.v1.controller;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
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

@RestController
@RequestMapping(value = "path = /restaurantes/{restauranteId}/formas-pagamento")
		//antes: @RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")	//aula 20.14
public class RestauranteFormaPagamentoController {		//aulas 12.12, 19.27, 20.14, 23.27, 23.28, 23.40
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired  //para converter FormaPagamentoModel para FormaPagamento
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.27
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.40

	@CheckSecurity.Restaurantes.PodeConsultar	//aula 23.27
	@GetMapping	//RequestMapping definie URI:/restaurantes/{restauranteId}/formas-pagamento
	//abaixo: aula 19.28, incluindo link para o método "desassociar"
	public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {  
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		CollectionModel<FormaPagamentoModel> formasPagamentoModel
				= formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
				.removeLinks();
		//abaixo, aula 23.40, até o final
		formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));

		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
			formasPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
			
			formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
				formaPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
						restauranteId, formaPagamentoModel.getId(), "desassociar"));
			});
		}
		
		return formasPagamentoModel;
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);	//aula 23.40
		
		return ResponseEntity.noContent().build();
	}
}




/*................................................................trecho comentado na aula 23.40
				.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
				.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));  //aula 19.29
		formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
			formaPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
					restauranteId, formaPagamentoModel.getId(), "desassociar"));
		});
		return formasPagamentoModel;
	}
	
	*........................................aula 12.12.................................*
	//@CheckSecurity.Restaurantes.PodeEditar		//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento	//aula 23.28
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
	cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	return ResponseEntity.noContent().build();		//aula 19.28, inclusive: ResponseEntity<Void> 
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar		//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento	//aula 23.28
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {		//aula 19.29
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();		//aula 19.29, inclusive: ResponseEntity<Void>
	}
	*..............................aula 12.12 - final...................................*
}
* .................................................método alterado na aula 19.28................
//	public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {	//aula 19.27
public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	//Acima: se existir o "id" requerido, continua.Se não resulta numa exception(mensagem)
	return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
	//Acima: Recebe FormasPagamento, já convertida no Assembler
	.removeLinks()
	.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));	//aula 19.27
}
.................................................................................aula 23.40.........*/