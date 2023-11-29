package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInput {	//aula 12.7, para representação de Inserir(POST) e atualizar(PUT)

	@NotBlank
	private String cep;
	
	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String numero;
	
	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;	//para pegar cidade do endereço pelo "Id"
	
}