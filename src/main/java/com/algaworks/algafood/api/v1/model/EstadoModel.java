package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")
@Setter
@Getter
//public class EstadoModel {		//aula 11.20,	19.14
public class EstadoModel extends RepresentationModel<EstadoModel> {
	private Long id;
	private String nome;
	
}

