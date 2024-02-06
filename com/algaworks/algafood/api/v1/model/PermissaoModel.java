package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;
@Relation(collectionRelation = "permissoes")
@Setter
@Getter
//	public class PermissaoModel {		//aula 19.34
	public class PermissaoModel extends RepresentationModel<PermissaoModel> {		//aula 19.34
	private Long id;
	private String nome;
	private String descricao;
}