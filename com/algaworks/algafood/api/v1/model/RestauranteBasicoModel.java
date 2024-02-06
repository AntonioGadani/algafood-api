package com.algaworks.algafood.api.v1.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter		//aula 19.24
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel> {
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaModel cozinha;
}