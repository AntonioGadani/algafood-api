package com.algaworks.algafood.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {	//aula 9.19
	
	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;

}