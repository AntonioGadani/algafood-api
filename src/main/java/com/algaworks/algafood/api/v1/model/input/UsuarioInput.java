package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioInput {			//aula 12.9
	/*Para inclusão de dados no banco(inserção ou atualização), somente a propriedade
	  "nome", de domain.model.Grupo será permitida    */

	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	
}