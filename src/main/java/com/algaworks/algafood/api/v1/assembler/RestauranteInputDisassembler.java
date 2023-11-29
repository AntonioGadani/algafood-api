package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component	//Classe é um componente do Spring
public class RestauranteInputDisassembler {		//11.13, 11.14, 11.17, 12.7

	@Autowired
	private ModelMapper modelmapper;
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
/*		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;		Código substituído pela biblioteca modelmapper -aula 11.14	} */
		return modelmapper.map(restauranteInput, Restaurante.class);
		//Acima: (origem: RestauranteInput; destino: Restaurante
	}

	/*abaixo, método p/eliminar BeanUtils.copyProperties em RestauranteControllerusando modelmapper	
												aula 11.17    */
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// abaixo, //Se cozinha.id alterada
		//Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
			restaurante.setCozinha(new Cozinha());
			
			if (restaurante.getEndereco() != null) {		//aula 12.7
				restaurante.getEndereco().setCidade(new Cidade());//igual setCozinha(anterior,acima)
			}
			modelmapper.map(restauranteInput, restaurante);
		}
		
	}