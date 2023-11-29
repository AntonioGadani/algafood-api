package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository		//aula 5.11, 5.12, 5.19
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired	@Lazy	//Lazy: anotação do Spring Framework
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find(String nome, /*método "find", na interface...................... 
	........................RestauranteRepositoryQueries, extends na classe RestauranteRepository */
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		var jpql = new StringBuilder(); //construtor de strings p/concatenar consulta JPQL. aula 5.12
		jpql.append("from Restaurante where 0 = 0 ");  /*append=acrescentar; 0=0(sempre verdadeiro 
		 para evita consultas sem a entidade(no caso, Restaurante). Atenção:incluir espaço: 0=0 "*/		
		var parametros = new HashMap<String, Object>();
		
		/*abaixo, classe utilitária StringUtils, 
		 * com método hasLenght que verifica se nome está nulo ou vazio*/
		if (StringUtils.hasLength(nome)) {
		
			//segue uma concatenação: nome, taxaInicial e taxaFinal
			jpql.append("and nome like :nome ");//append=acrescentar,:nome "(parâmetro+espaço + ") 
			parametros.put("nome", "%" + nome + "%");
		}
		
		if (taxaFreteInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");//append=acrescentar,:taxaInicial "(parâmetro+espaço + ") 
			parametros.put("taxaInicial", taxaFreteInicial);
		}
		
		if (taxaFreteFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");//append=acrescentar,:taxaFinal "(parâmetro+espaço + ")
			parametros.put("taxaFinal", taxaFreteFinal);
		}
		
		TypedQuery<Restaurante> query = manager
				.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

		return query.getResultList();
	}
	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis()
				.and(comNomeSemelhante(nome)));
	}
}