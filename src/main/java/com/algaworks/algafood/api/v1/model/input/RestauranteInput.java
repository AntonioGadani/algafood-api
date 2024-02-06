package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInput {		//aula 11.11, 12.7

	@NotBlank
	private String nome;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;	//pega o "id" da cozinha na classe CozinhaIdInput
	
	@Valid
	@NotNull
	private EnderecoInput endereco;		//aula 12.7, propriedade adicionada
	
}