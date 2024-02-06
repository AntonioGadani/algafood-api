package com.algaworks.algafood.api.v1.controller;
import com.algaworks.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)	//@RequestMapping(value = "/pedidos")	//aula 20.14
public class PedidoController implements PedidoControllerOpenApi{	//aulas 12.19, 12.20, 12.21, 12.25, 13.2, 
				                         //aulas 13.6, 19.16, 19.17, 20.14, 23.17, 23.29, 23.30, 23.31, 27.3
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;	//aula 12.21
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;	//aula 19.16
	
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.17
	
	@CheckSecurity.Pedidos.PodePesquisar	//aula 23.30
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
            @PageableDefault(size = 10) Pageable pageable) {	//aula 19.17
			Pageable pageableTraduzido = traduzirPageable(pageable);

			Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

				pedidosPage = new PageWrapper<>(pedidosPage, pageable);
			return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
}
	
	@CheckSecurity.Pedidos.PodeCriar		//aula 23.31
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {	//aula 12.21
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			novoPedido.setCliente(new Usuario());	// Todo dava warning pegar usuário autenticado
			//novoPedido.getCliente().setId(1L);
			novoPedido.getCliente().setId(algaSecurity.getUsuarioId());	//aula 23.17
			novoPedido = emissaoPedido.emitir(novoPedido);
			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Pedidos.PodeBuscar		//aula 23.29
	@Override
	@GetMapping("/{codigoPedido}")		//@GetMapping("/{pedidoId}")    aula 12.25
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		return pedidoModelAssembler.toModel(pedido);
	}
	
	//abaixo: ..................................................................aula 13.12
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome"
			);
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	//acima: ..................................................................aula 13.12
}

/*...................Código descartado........................................coment: aula 13.9	
@GetMapping
//public List<PedidoResumoModel> listar() {    alterado nome na aula 13.6
	public List<PedidoResumoModel> pesquisar(PedidoFilter filtro) {		//aula 13.6
	//List<Pedido> todosPedidos = pedidoRepository.findAll();   alterado na aula 13.6
	List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
	return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
}
.....................................................................................
@GetMapping			//aula 13.9
public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
        @PageableDefault(size = 10) Pageable pageable) {
	pageable = traduzirPageable(pageable);     //aula 13.11, método declarado abaixo, nesta classe
    Page<Pedido> pedidosPage = pedidoRepository.findAll(
            PedidoSpecs.usandoFiltro(filtro), pageable);
    List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
            .toCollectionModel(pedidosPage.getContent());
    
    Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(
            pedidosResumoModel, pageable, pedidosPage.getTotalElements());
	 return pedidosResumoModelPage;
}

*.....................aula 13.2. Abaixo:p/listar requisita parâmetro(Campos), más,not required
@GetMapping
public MappingJacksonValue listar(@RequestParam(required = false) String campos) {	
	List<Pedido> pedidos = pedidoRepository.findAll();
	List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
	MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
	SimpleFilterProvider filterProvider = new SimpleFilterProvider();
	//abaixo, serializeAll()), é padrão. Se não informar parâmetro "campos", representa todas propriedades
	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
	
	if (StringUtils.isNotBlank(campos)) { //se informa parâmetro, usuário informa propreidades requeridas
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.
				filterOutAllExcept(campos.split(","))); //split: quebra os campos separando por"," 
	}
	pedidosWrapper.setFilters(filterProvider);
	return pedidosWrapper;
}	aula 13.2. ...................................................final........*/	
/*...........................................................................aula 19.16
public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
		@PageableDefault(size = 10) Pageable pageable) {
	pageable = traduzirPageable(pageable);
	
	Page<Pedido> pedidosPage = pedidoRepository.findAll(
			PedidoSpecs.usandoFiltro(filtro), pageable);
	
	return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
}
..........................................................................aula 19.16....*/



