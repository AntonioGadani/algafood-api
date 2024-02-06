package com.algaworks.algafood.api.v1.controller;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("path = /v1/formas-pagamento")	//ante: @RequestMapping("/formas-pagamento")	//aula 20.14
public class FormaPagamentoController {		//aulas: 12.5, 17.2, 17.9, 17.10, 17.27, 19.14, 23.32

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@CheckSecurity.FormasPagamento.PodeConsultar	//aula 23.32
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());	//aula 19.27
		String eTag = "0";
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
		CollectionModel<FormaPagamentoModel> formasPagamentosModel =
				formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentosModel);
	}
	
	@CheckSecurity.FormasPagamento.PodeConsultar	//aula 23.32
	@GetMapping("/{formaPagamentoId}")		//aula 17.10
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId,
			ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		OffsetDateTime dataAtualizacao = formaPagamentoRepository
				.getDataAtualizacaoById(formaPagamentoId);
		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
			return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar	//aula 23.32
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) /*Se, tudo de acordo, 
	                      em CadastroFormaPagamentoService.salvar,  Retorna Status 201       */
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		/*abaixo: método toDomainObject, abaixo, 
		  converte converte restauranteInput para restaurante.
		  Feito isso, pode salvar um restaurante na entidade Restaurante */
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		
		formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
		
		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar	//aula 23.32
	@PutMapping("/{formaPagamentoId}")	/*   ver mesmo método atualizado em 
										     RestauranteController, na aula 11.17    */
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		//Abaixo: Para verificação,chama método buscarOuFalhar 
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		/* abaixo: método copyToDomainObject, em FormaPagamentoInputDisassembler, converte 
		   FormaPagamentoInput para FormaPagamento, ficando os dados aptos para serem 
		   salvos na classe de domínio: FormaPagamento   */
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		
		formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);
		
		return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar	//aula 23.32
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);	
	}
}
/*............................................alterado na aula 17.2	
@GetMapping
public List<FormaPagamentoModel> listar() {
	List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
	return formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);
/*  Assembler chama coleção FormaPagamentoModel para representação    
}
............................................alterado na aula 17.2..................*/	
/*............................................alterado na aula 17.27
@GetMapping
public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request) {//aula 17.2
	//abaixo: desabilitamos o ShallowEtag para implantar o deepEtag
	ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());	//aula 17.9
	String eTag = "0";
	OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		if(dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
			//Converts this date-time to the number of seconds from the epoch of 1970-01-01T00:00:00Z
		}
		if (request.checkNotModified(eTag)) {	//se não houve modificação
			return null;
		}	//e continua a executar
	List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
			List<FormaPagamentoModel> formasPagamentosModel = formaPagamentoModelAssembler
			.toCollectionModel(todasFormasPagamentos);
	
	return ResponseEntity.ok()
			//.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))  alteraldo aula 17.6
			.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
			.eTag(eTag)
			//.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())    padrão é public
			//.cacheControl(CacheControl.noCache())    aula 17.6, Sempre aciona CACHE, pivate ou public
			//.cacheControl(CacheControl.noStore())   //aula 17.6, nenhu cache pode armazenar a resposta
			.body(formasPagamentosModel);
}
.........................................................................alterado na aula 17.3	
@GetMapping("/{formaPagamentoId}")
public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {
	FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
	return formaPagamentoModelAssembler.toModel(formaPagamento);
//   Assembler chama recurso unitário de FormaPagamentoModel 
}
..................................................................................alterado na aula 17.3*/
/*	@GetMapping("/{formaPagamentoId}")		//aula 17.3, 17.10
public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId) {
	FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
	
	FormaPagamentoModel formaPagamentoModel =  formaPagamentoModelAssembler.toModel(formaPagamento);
	
	return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
			.body(formaPagamentoModel);
}.............................................alterado na aula 17.10 e 19.27   */