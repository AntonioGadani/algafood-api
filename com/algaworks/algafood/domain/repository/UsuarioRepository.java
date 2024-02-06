package com.algaworks.algafood.domain.repository;

import java.util.Optional;

//import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Usuario;

//@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{//aulas 12.9, 12.11
	Optional<Usuario> findByEmail(String email);	//aula 11.12, evitando duplicar email
}