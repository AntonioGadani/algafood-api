package com.algaworks.algafood.api.v1.model;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;
@Relation(collectionRelation = "usuarios")  	//aula 19.12, p/definir título do link "usuarioModelList"
@Setter
@Getter
//public class UsuarioModel  {		//aula 12.9, 19.12
	public class UsuarioModel extends RepresentationModel<UsuarioModel>{
	/*  Esta classe define quais propriedade da classe de domínio(domain.Model.Usuario), 
    queremos permitir em uma representação de recursos(busca ou listagem).*/
	private Long id;
	private String nome;
	private String email;
	
}