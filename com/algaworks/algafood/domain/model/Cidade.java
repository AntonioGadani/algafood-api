package com.algaworks.algafood.domain.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
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
