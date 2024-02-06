package com.algaworks.algafood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {  //aula 14.3

	private DataSize maxSize; //DataSize: classe do Spring que representa um tamanho de dados
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		//abaixo: maxSize = valor do DataSize convertido para número 
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		//abaixo: Está válido se retornar um valor nulo ||(ou) menor ou igual a maxSize convertido em Bytes
		return value == null || value.getSize() <= this.maxSize.toBytes();
	}

}