package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")//aula 19.16,p/definir título:link"RestauranteModelList"
@Setter
@Getter
//public class RestauranteResumoModel { 
//acima:aula 12.19//só usaremos 2 propriedades da classe Restaurante model /representação de Pedidos
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> { //aula 19.16
	private Long id;
	private String nome;
	
}