package com.algaworks.algafood.api.v1.model;
import java.math.BigDecimal;
import org.springframework.hateoas.RepresentationModel;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
//public class ItemPedidoModel {			//aula 12.19
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {	/*aula 19.16
	acima: RepresentationModel para definir links na representação    */
	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
}