package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInput {		//aula 11.20

	@NotBlank
	private String nome;
	
}