package com.algaworks.algafood.api.v2.model.input;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInputV2 {	//aula 20.11
//	@ApiModelProperty(example = "Uberlândia", required = true)
	@NotBlank
	private String nomeCidade;
//	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long idEstado;
}