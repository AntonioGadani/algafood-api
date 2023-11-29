package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
//import com.algaworks.algafood.api.model.input.RestauranteIdInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {		//aula 12.21

	@Autowired
	private ModelMapper modelMapper;
	public Pedido toDomainObject(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
		/*RestauranteIdInput= new restaurante;
		 * EnderecoInput = new enderecoEntrega
		 * FormaPagamentoIdInput = new formaPagamento.      
		 * 		Tudo substituído pelo uso da biblioteca ModelMapper, aula 11.14 */
	}
	public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
		modelMapper.map(pedidoInput, pedido);	/*Ao contrário de Assembler, 
		pega todas as propriedades da origem: RestauranteInput e gravam no destino: Restaurante  */
	}
}