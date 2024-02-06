package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoInput {

	@NotBlank
	private String nome;
	/*Para inclusão de dados no banco(inserção ou atualização), somente a propriedade
	  "nome", de domain.model.Grupo será permitida    */
	
}