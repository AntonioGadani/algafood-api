package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//	public class ProdutoModel {		//aulas 12.13, 19.31
	public class ProdutoModel extends RepresentationModel<ProdutoModel> {		//aulas 19.31
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Boolean ativo;	
}