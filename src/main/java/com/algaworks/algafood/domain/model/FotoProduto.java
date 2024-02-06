package com.algaworks.algafood.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {		//aula 14.5, 14.7

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long id;
	
	/*abaixo: relacionamento: Um produto p/ uma foto, uma foto p/um produto. Não é feito relacionamento
	 		bi-direcional  na entidade "produto". Ver matéria a respeito na aula 14.5*/
	@OneToOne(fetch = FetchType.LAZY)  //FetchType.LAZY evita SELECTs desnecessários
	@MapsId	//entidade "produto é mapeada através do id de "fotoProduto
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	public Long getRestauranteId() {		//aula 14.7
		if (getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		
		return null;
	}
	
}