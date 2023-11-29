package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoInput {		//aula 12.21

	@NotNull
	private Long produtoId;
	
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	private String observacao;
}