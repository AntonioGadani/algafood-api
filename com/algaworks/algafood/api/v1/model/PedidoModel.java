package com.algaworks.algafood.api.v1.model;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;
@Relation(collectionRelation = "pedidos")//aula 19.16,p/definir título:link"pedidoModelList"
@Setter
@Getter
//public class PedidoModel {		//aula 12.19, 12.25, 19.24
public class PedidoModel extends RepresentationModel<PedidoModel> {/*aula 19.16
	acima: RepresentationModel para definir links na representação    */
	private String codigo;		//private Long id;		aula 12.25
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	// private RestauranteResumoModel restaurante;		//aula 19.24
	private RestauranteApenasNomeModel restaurante;		//aula 19.24
	private UsuarioModel cliente;
	private FormaPagamentoModel formaPagamento;
	private EnderecoModel enderecoEntrega;
	private List<ItemPedidoModel> itens;
}