package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInput {		//aula 11.20

	@NotBlank
	private String nome;
	
}