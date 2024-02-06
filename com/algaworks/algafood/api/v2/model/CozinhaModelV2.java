package com.algaworks.algafood.api.v2.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {	//aula 20.15

//	@ApiModelProperty(example = "1")
	private Long idCozinha;
	
//	@ApiModelProperty(example = "Brasileira")
	private String nomeCozinha;
	
}