package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component		//classe é um componente do Spring
public class GrupoInputDisassembler {   /*aula 12.8  Ver criação de 
    RestauranteInputDisassembler nas aulas 11.13, 11.14, 11.17  */

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	/*Abaixo: início de estudo da  biblioteca modelmapper, em RestauranteInputDisassembler
	  na aula 11.14 		 */
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
	
}