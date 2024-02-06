package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.algaworks.algafood.domain.model.Cidade;

//@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}