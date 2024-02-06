package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {		//aula 14.2, 14.3, 14.4
	@NotNull					//aula 14.3	
	@FileSize(max = "500KB")	//aula 14.3
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })	//aula 14.4
	private MultipartFile arquivo;//MultipartFile(nterface Spring)org.springframework.web.multipart.MultipartFile
	
	@NotBlank	//aula 14.3
	private String descricao;
	
}