package com.algaworks.algafood.api.v2.model.input;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInputV2 {	//aula 20.15

	@NotBlank
	private String nomeCozinha;
	
}