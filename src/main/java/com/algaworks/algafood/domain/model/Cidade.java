package com.algaworks.algafood.domain.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import com.algaworks.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data     /*Todos juntos agora: um atalho para @ToString, @EqualsAndHashCode, 
@Getter em todos os campos e @Setter em todos os campos não finais 
e @RequiredArgsConstructor!	*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {	//aula 3.20, 4.32, 9.7, 9.8, 11.2, 11.4, 11.20
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NotNull		//coment: aula 11.20
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@Valid	//valide a propriedade estado
	@ConvertGroup(from = Default.class, to = Groups.CidadeId.class)	//aula 9.8
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;
}
