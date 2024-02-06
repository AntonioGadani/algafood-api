package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data     /*Todos juntos agora: um atalho para @ToString, @EqualsAndHashCode, 
@Getter em todos os campos e @Setter em todos os campos não finais 
e @RequiredArgsConstructor!	*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {		//aulas: 6.9, 12.8, 12.14
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id"),
			inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	//private List<Permissao> permissoes = new ArrayList<>();   coment: aula 12.15
/*..............................................................aula 12.15......................*/
	private Set<Permissao> permissoes = new HashSet<>();
	
	public boolean removerPermissao(Permissao permissao) {
		return getPermissoes().remove(permissao);
	}
	
	public boolean adicionarPermissao(Permissao permissao) {
		return getPermissoes().add(permissao);
	}
	/*..............................................................aula 12.15......................*/
}
