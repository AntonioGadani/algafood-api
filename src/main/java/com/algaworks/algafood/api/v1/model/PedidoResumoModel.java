package com.algaworks.algafood.api.v1.model;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")	//aula 13.2
@Relation(collectionRelation = "pedidos")//aula 19.16,p/definir título:link"pedidoModelList"
@Setter
@Getter
//public class PedidoResumoModel {		//aula 12.20, 12.25, 13.2, 19,24
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> { /*aula 19.16
		acima: RepresentationModel para definir links na representação    */
	private String codigo;		//private Long id;	aula12.25
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	//private RestauranteResumoModel restaurante;		//aula 19.24
	private RestauranteApenasNomeModel restaurante;		//aula 19.24
	private UsuarioModel cliente;
}