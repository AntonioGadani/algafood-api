package com.algaworks.algafood.core.modelmapper;
import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ModelMapperConfig {	 //aula 11.14,  11.16,  12.6,  12.21, 20.11

	@Bean
	ModelMapper modelMapper() {		//public não é mais necessário
		var modelMapper = new ModelMapper();
//abaixo: quando estiver criando input para cidade(versão 2), ignorar id da cidade
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
				.addMappings(mapper -> mapper.skip(Cidade::setId));

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoModel.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
}
/* ...........................................................................código comentado na aula 20.11							
	@Bean
	ModelMapper modelMapper() {				//public parece não ser mais necessário
		var modelMapper = new ModelMapper();
		*modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class) 
  			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);   
  																coment: aula 12.6    *
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)	//aula 20.11
		.addMappings(mapper -> mapper.skip(Cidade::setId));
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class) 
					      *Acima:(converte itemPedidoInput para itemPedido	*
		.addMappings(mapper -> mapper.skip(ItemPedido::setId)); //skip(pula o id do itemPedido
		//Abaixo, alteração feita na aula 12.6, mapeando endereco para enderecoModel            
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoModel.class);
		*abaixo: enderecoSrc=origem(pega nome:estado da cidade doendereco; 
		          destino(para enderecoModel.cidade       *
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		return modelMapper;
	}
}
.....................................................................Fim código comentado aula 20.11*/