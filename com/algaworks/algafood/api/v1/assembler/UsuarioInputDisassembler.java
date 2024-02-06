package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component	//classe é um componente do Spring
public class UsuarioInputDisassembler {		/*aula 12.9   Ver criação de 
    RestauranteInputDisassembler nas aulas 11.13, 11.14, 11.17  */

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	/*Abaixo: início de estudo da  biblioteca modelmapper, em RestauranteInputDisassembler
	  na aula 11.14 		 */
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
}