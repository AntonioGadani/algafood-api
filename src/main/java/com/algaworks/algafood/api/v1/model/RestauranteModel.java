package com.algaworks.algafood.api.v1.model;
import java.math.BigDecimal;
import org.springframework.hateoas.RepresentationModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel  //aula 11.10, 11.16, 12.4, 12.6, 12.14, 13.1, 19.24
		extends RepresentationModel<RestauranteModel> {
//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })//aula 13.1, 19.24
	private Long id;
//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })//aula 13.1, 19.24
	private String nome;
//	@JsonView(RestauranteView.Resumo.class)	//classe 13.1, 19.24
	private BigDecimal taxaFrete;
	//private BigDecimal precoFrete;
//	@JsonView(RestauranteView.Resumo.class)	//classe 13.1, 19.24
	private CozinhaModel cozinha;
	private Boolean ativo;    //aula 12.4
	private Boolean aberto;	  //aula 12.14
	private EnderecoModel endereco;  //aula 12.6
}