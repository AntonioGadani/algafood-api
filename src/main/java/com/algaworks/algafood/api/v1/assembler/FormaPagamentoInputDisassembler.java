package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component	//classe é um componente do Spring
public class FormaPagamentoInputDisassembler {	/*aula 12.5  Ver criação de 
                            RestauranteInputDisassembler nas aulas 11.13, 11.14, 11.17  */
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
		/*Acima: início de estudo da  biblioteca modelmapper, em RestauranteInputDisassembler
		  na aula 11.14 		 */
	}
	
}