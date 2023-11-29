package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaIdInput {		//aulas: 11.11, 11.20

	@NotNull
	private Long id;	//aula 11.11, classe prá informar "id" da cozinha em RestauranteInput
	
}