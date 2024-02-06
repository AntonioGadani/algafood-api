package com.algaworks.algafood.domain.repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
import com.algaworks.algafood.domain.model.Restaurante;

//@Repository
public interface RestauranteRepository 
		extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
		JpaSpecificationExecutor<Restaurante> {

	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinha);
	
	boolean existsResponsavel(Long restauranteId, Long usuarioId);
	
}
/*....................................................classe reescrita na aula 27.3...código antigo abaixo
package com.algaworks.algafood.domain.repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.algaworks.algafood.domain.model.Restaurante;
//@Repository	//aulas 3.16, 5.5, 5.10, 5.11, 5.17, 5.19, 5.20, 6.14, 8.6, 23.28, 27.3
public interface RestauranteRepository 
				extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
				JpaSpecificationExecutor<Restaurante>{  //JpaSpecificationExecutor-aula 5.17
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")//comentada na aula 5.10
	//abaixo, esta consulta necessita do arquivo orm.xml para funcionar.(src/main/resources/META-INF
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha); //aula 5.10
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	List<Restaurante> findTop2ByNomeContaining(String nome);
	int countByCozinhaId(Long cozinha);
	boolean existsResponsavel(Long restauranteId, Long usuarioId); //aula 23.28. Query em ORM.xml
}

...............................................................classe reescrita na aula 27.3......*/