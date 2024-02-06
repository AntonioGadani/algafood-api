package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
//public class PedidoResumoModelAssembler {	//aula 12.20, 19.21, 19.26, 23.40
public class PedidoResumoModelAssembler 
		extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {
		//acima: Pedido: Origem dos dados;	PedidoResumoModel: Representação dos dados
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.21
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}
	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(),pedido);
		modelMapper.map(pedido, pedidoModel);
		
		if (algaSecurity.podePesquisarPedidos()) {		//aula 23.40
			pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {		//aula 23.40
			pedidoModel.getRestaurante().add(
					algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}

		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {		//aula 23.40
			pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		}
		
		return pedidoModel;
	}
}
/*		pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));	//aula 19.21 e 23.40
					pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
							.buscar(pedido.getRestaurante().getId())).withSelfRel());
			    
	    		pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
								.buscar(pedido.getCliente().getId())).withSelfRel());..........*
//	    pedidoModel.add(algaLinks.linkToPedidos()); //links inclusos na classe AlgaLinks, aula 19.21		
	    pedidoModel.add(algaLinks.linkToPedidos("pedidos"));// aula 19.26
	    pedidoModel.getRestaurante().add(
	            algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
	    pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		return pedidoModel;
	}
}............................................................................................aula 23.40.*/