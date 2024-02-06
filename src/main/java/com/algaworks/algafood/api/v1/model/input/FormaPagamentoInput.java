package com.algaworks.algafood.api.v1.model.input;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoInput {		//aula 12.5
	@NotBlank
	private String descricao;
	/*Para inclusão de dados no banco(inserção ou atualização), somente a propriedade
	  "descricao", de domain.model.FormaPagamento será permitida    */
}