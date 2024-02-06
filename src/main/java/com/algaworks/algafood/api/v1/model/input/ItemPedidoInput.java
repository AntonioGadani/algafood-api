package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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