package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteIdInput {//aula 12.21, p inserir somente o id do restaurante(nas entidades)

	@NotNull
	private Long id;
}