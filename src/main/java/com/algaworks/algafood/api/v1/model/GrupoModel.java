package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//	public class GrupoModel {		//aula 12.8
	public class GrupoModel extends RepresentationModel<GrupoModel> {		//aula 19.33
	/*  Esta classe define quais propriedade da classe de domínio(domain.Model.Grupo), 
    queremos permitir em uma representação de recursos(busca ou listagem).*/
	private Long id;
	private String nome;
}
