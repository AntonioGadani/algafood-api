package com.algaworks.algafood.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean	//Spring deve ignorar esta interface como repositório
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {  // aulas  5.20, 12.11
	Optional<T> buscarPrimeiro();
	void detach(T entity);  //detash interrompe transação antes de uma consulta
	
}