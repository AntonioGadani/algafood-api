package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;
@Relation(collectionRelation = "formasPagamento")
//acima: aula 19.16,p/definir título:link"formasPagamentoModelList"
@Setter
@Getter
//public class FormaPagamentoModel {		//aula 12.5
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {  /*aula 19.16
	acima: RepresentationModel para definir links na representação    */
	private Long id;
	private String descricao;
	/*  Esta classe define quais propriedade da classe de domínio(domain.Model.FormaPagamento), 
	    queremos permitir em uma representação de recursos(busca ou listagem).	            */
}