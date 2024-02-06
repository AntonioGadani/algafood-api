package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")  //aula 19.15, p/definir título do link "cozinhaModelList"
@Setter
@Getter		//aula 11.10, 11.20, 19.15, 19.24
public class CozinhaModel extends RepresentationModel<CozinhaModel> {
//	@JsonView(RestauranteView.Resumo.class)		//aula 19.24
	private Long id;
//	@JsonView(RestauranteView.Resumo.class)		//aula 19.24
	private String nome;
}