package com.algaworks.algafood.domain.model;
import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data     /*Todos juntos agora: um atalho para @ToString, @EqualsAndHashCode, 
@Getter em todos os campos e @Setter em todos os campos não finais 
e @RequiredArgsConstructor!	*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {	//aula 3.20, 12.5
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@NotNull	anotação usada em FormaPagamentoModel
	@Column(nullable = false)
	private String descricao;
	@UpdateTimestamp	//aula 17.9
	private OffsetDateTime dataAtualizacao;

}
