package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")  //aula 19.10, p/definir título do link "cidadeModelList"
@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel> {	//aula 11.20, 19.7
	private Long id;
	private String nome;
	private EstadoModel estado;
	
}