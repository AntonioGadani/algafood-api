package com.algaworks.algafood.api.v1.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
//@CrossOrigin(maxAge = 10)	// é padrão(origins = "*") /maxAge = 10:tempo máximo p/ficar armazenada no cash
//@CrossOrigin(origins = "http://www.algafood.local:5500")
@RestController	//é um controlador e retornará respostas conforme requisições HTTP
@RequestMapping(path = "/v1/restaurantes")	//antes: @RequestMapping(value = "/restaurantes")	//aula 20.14
public class RestauranteController {/*aulas: 4.29, 4.30, 4.31, 4.33, 4.34,5.5, 6.3, 8.6, 8.9, 8.11
	8.24, 9.2, 9.7,  9.8,  9.19, 11.10, 11.11, 11.12, 11.13, 11.17, 12.4, 12.7, 12.14, 12.18, 13.1
	16.3, 16.4, 16.6, 19.24, 20.14, 23.27 */
	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;   //aula 19.24
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;   //aula 19.24
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
//	@JsonView(RestauranteView.Resumo.class)		//aula 13.1, coment: aula 19.24

	@CheckSecurity.Restaurantes.PodeConsultar	//aula 23.27
	@GetMapping
//	public List<RestauranteModel> listar() {		//coment: aula 19.24
	public CollectionModel<RestauranteBasicoModel> listar() {	//aula 19.24
		return restauranteBasicoModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
	}	
//	@JsonView(RestauranteView.ApenasNome.class)		//coment: aula 19.24

	@CheckSecurity.Restaurantes.PodeConsultar		//aula 23.27
	@GetMapping(params = "projecao=apenas-nome")
	/* public List<RestauranteModel> listarApenasNomes() {	//aula 13.1
		return listar();.............coment: aula 19.24...*/
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {	//aula 19.24
		return restauranteApenasNomeModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
	}

/*........................................método para fins didáticos na aula 16.3	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public ResponseEntity<List<RestauranteModel>> listar() {		//aula 16.3
		List<RestauranteModel> restaurantesModel = restauranteModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
				.body(restaurantesModel);
	}	........................................método para fins didáticos na aula 16.3*/						
	
	@CheckSecurity.Restaurantes.PodeConsultar	//aula 23.27
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		return restauranteModelAssembler.toModel(restaurante);		/*propriedades setadas no 
		método "toModel", declarado na classe restauranteModelAssembler		*/
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro	//aula 23.28
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	//Se, tudo de acordo, em CadastroCidadeService.salvar, Retorna Status 201
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			/*abaixo: método toDomainObject, abaixo, converte converte restauranteInput 
			   para restaurante.Feito isso, pode salvar um restaurante na entidade 
			   Restaurante */
			Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro	//aula 23.28
	@PutMapping("/{restauranteId}")		//método atualizado na aula 11.17
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
/*		try {		Trecho substituído na aula 11.17, p/ utilizar biblioteca modelmapper
		//abaixo: método toDomainObject, abaixo, converte converte restaurante p/ restauranteInput
		Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
		//abaixo: //para verificação,chama método buscarOuFalhar			
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
		//abaixo,copiar Id,formasPagamento,endereco,dataCadastro e produtos de restaurante,atribuindo p/restauranteAtual	
		BeanUtils.copyProperties(restaurante, restauranteAtual, 
			"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));  */
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			//abaixo, método em RestauranteInputDisassembler
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
	} catch (CozinhaNaoEncontradaException|CidadeNaoEncontradaException e) {  //aula 12.7
			throw new NegocioException(e.getMessage());	//recebe status http 400(Bad Request)
		}
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro	//aula 23.28
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//public void> ativar(@PathVariable Long restauranteId) {		//coment: aula 19.24
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {	//aula 19.24
		cadastroRestaurante.ativar(restauranteId);
		return ResponseEntity.noContent().build();		//aula 19.24
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro	//aula 23.28
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//public void inativar(@PathVariable Long restauranteId) {		//coment: aula 19.24
		public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {	//aula 19.24
		cadastroRestaurante.inativar(restauranteId);
		return ResponseEntity.noContent().build();	//aula 19.24
	}
/*.........................aula 12.18..........................................................*/
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro	//aula 23.28
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro	//aula 23.28
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
/*.........................aula 12.18  fim.....................................................*/
/*..................................................aula 12.14....................................*/	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento	//aula 23.28
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void abrir(@PathVariable Long restauranteId) {		//coment: aula 19.24
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {		//aula 19.24
		cadastroRestaurante.abrir(restauranteId);
		return ResponseEntity.noContent().build();		//aula 19.24
	}
	
	//@CheckSecurity.Restaurantes.PodeEditar	//aula 23.27
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento	//aula 23.28
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void fechar(@PathVariable Long restauranteId) {		//coment: aula 19.24
		public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {	//aula 19.24
		cadastroRestaurante.fechar(restauranteId);
		return ResponseEntity.noContent().build();	//aula 19.24
	}
}
/*...................................................aula 12.14 fim............................*/

	/* métodos transferidos para classe RestauranteModelAssembler, na aula 11.12
	private RestauranteModel toModel(Restaurante restaurante) { //para setar propriedades em vários pontos
	private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
	 */
	//método transferido para classe RestauranteInputDisassembler
	
