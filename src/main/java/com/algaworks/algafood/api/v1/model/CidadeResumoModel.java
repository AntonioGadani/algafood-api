package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;
@Relation(collectionRelation = "cidades")//aula 19.16,p/definir título:link"cidadesModelList"
@Setter
@Getter
//blic class CidadeResumoModel {		//aula 12.6, para ser acessada por EnderecoModel
	public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {/*aula 19.16
	acima: RepresentationModel para definir links na representação    */
	private Long id;
	private String nome;
	private String estado;
}