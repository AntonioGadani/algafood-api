package com.algaworks.algafood.api.v1.assembler;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
@Component	//classe é um componente do Spring
//public class PedidoModelAssembler {			//aula 12.19
public class PedidoModelAssembler 	//aula 19.16, 19.18, 19.21, 19.23, 19.26, 23.40
		extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {	//aula 19.16, 19.18
				//acima: Pedido: Origem dos dados;	PedidoModel: Representação dos dados
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.20
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.39

	public PedidoModelAssembler() {		//construtor
		super(PedidoController.class, PedidoModel.class);
	}
		@Override
		public PedidoModel toModel(Pedido pedido) {
			PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
			modelMapper.map(pedido, pedidoModel);
			
			// Não usei o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
			// porque na geração do link, não temos o id do cliente e do restaurante, 
			// então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
			if (algaSecurity.podePesquisarPedidos()) {	//aula 23.40, todo o restante do código
				pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
			}
			
			if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
				if (pedido.podeSerConfirmado()) {
					pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
				}
				
				if (pedido.podeSerCancelado()) {
					pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
				}
				
				if (pedido.podeSerEntregue()) {
					pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
				}
			}
			
			if (algaSecurity.podeConsultarRestaurantes()) {
				pedidoModel.getRestaurante().add(
						algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
			}
			
			if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
				pedidoModel.getCliente().add(
						algaLinks.linkToUsuario(pedido.getCliente().getId()));
			}
			
			if (algaSecurity.podeConsultarFormasPagamento()) {
				pedidoModel.getFormaPagamento().add(
						algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
			}
			
			if (algaSecurity.podeConsultarCidades()) {
				pedidoModel.getEnderecoEntrega().getCidade().add(
						algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
			}
			
			// Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
			if (algaSecurity.podeConsultarRestaurantes()) {
				pedidoModel.getItens().forEach(item -> {
					item.add(algaLinks.linkToProduto(
							pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
				});
			}
			
			return pedidoModel;
		}

	}
			
			
/*	pedidoModel.add(algaLinks.linkToPedidos());	//links p/Pedido, na classe Algalinks.....aula 23.40
			pedidoModel.add(algaLinks.linkToPedidos("pedidos"));	//aula 19.26
			//abaixo links para Status do pedido,   aula 19.22
			
		if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {	//aula 23.39
			if (pedido.podeSerConfirmado()) {	//aula 19.23
				pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}
			if (pedido.podeSerCancelado()) {	//aula 19.23
				pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}
			if (pedido.podeSerEntregue()) {	//aula 19.23
				pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
		}			
*			...................................................................................
			pedidoModel.getRestaurante().add(	//links p/Restaurante, na classe Algalinks
		            algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		    pedidoModel.getCliente().add(	//links p/Cliente, na classe Algalinks
		            algaLinks.linkToUsuario(pedido.getCliente().getId()));
		    pedidoModel.getFormaPagamento().add(	//links p/FormaPagamento, na classe Algalinks
		            algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		    pedidoModel.getEnderecoEntrega().getCidade().add(//links p/EnderecoEntrega:classe Algalinks
		            algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
		    
		    pedidoModel.getItens().forEach(item -> {
		        item.add(algaLinks.linkToProduto(//links p/Produto, na classe Algalinks
		                pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
		    });		    
		    return pedidoModel;
		}
}
*.....................................Tudo p/ classe Algalinks...........aula 19.20
//abaixo,acrescentando parâmetros(link representação)aulas 19.18 e 19.19. 
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		//pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos")); // coment: 
		TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM));
			TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
//		pedidoModel.add(Link.of(UriTemplate.of(pedidosUrl, pageVariables), "pedidos")); aula 19.19
		pedidoModel.add(Link.of(UriTemplate.of(pedidosUrl,	//aula 19.19
				pageVariables.concat(filtroVariables)), "pedidos"));		
//..acima,......acrescentando parâmetros no link da representação....aulas 19.18 e 19.19		

		pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
				.buscar(pedido.getRestaurante().getId())).withSelfRel());
		
		pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
				.buscar(pedido.getCliente().getId())).withSelfRel());
		
		// Passamos null no segundo argumento, porque é indiferente para a
		// construção da URL do recurso de forma de pagamento
		pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
				.buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());
		
		pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
				.buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());
		
		pedidoModel.getItens().forEach(item -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class)
					.buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId()))
					.withRel("produto"));
		});
		
		return pedidoModel;
	}
}
...................................Tudo acima        p/ classe Algalinks...........aula 19.20 
	* ............................................trecho de código substituído na aula 19.16  
	 Esta é uma classe que monta um PedidoModel(DTO) e, 
    será injetada no controlador(PedidoController   
    Aula 11.10 iniciou estudos  sobre Método toModel. 
    Abaixo, Pega todas as propriedades de  Pedido e cola em PedidoModel.
	public PedidoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoModel.class);
	}
	//abaixo, De uma lista de Pedido, devolvemos uma lista de pedidoModel...............
	
	public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()	*stream() para subdtituir codificação de laços   
				.map(pedido -> toModel(pedido))  //.map mapeia propriedades de Pedido para PedidoModel
				.collect(Collectors.toList());    //converte de uma lista lista para outra.
													lista de domain.model para api.model 
	}}......................................trecho de código substituído na aula 19.16, 23.40......*/