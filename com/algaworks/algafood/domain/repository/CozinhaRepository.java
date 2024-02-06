package com.algaworks.algafood.domain.repository;
import java.util.List;
import java.util.Optional;

//import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

//@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {		//aula 11.20

	List<Cozinha> findTodasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	boolean existsByNome(String nome);
	
}
