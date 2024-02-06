package com.algaworks.algafood.api.v1.model.input;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoInput {		//aula 12.21

	@Valid
	@NotNull
	private RestauranteIdInput restaurante;
	
	@Valid
	@NotNull
	private EnderecoInput enderecoEntrega;
	
	@Valid
	@NotNull
	private FormaPagamentoIdInput formaPagamento;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoInput> itens;
	
}