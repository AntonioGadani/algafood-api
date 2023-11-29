package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoIdInput {//aula 12.21:inserir somente o id(FormaPgto(nas entidades))

	@NotNull
	private Long id;
	
}