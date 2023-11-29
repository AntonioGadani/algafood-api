package com.algaworks.algafood.domain.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;	//aula 23.15
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {	//aulas 12.9, 12.11, 12.16, 23.15

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;	//aula 12.16
	
	@Autowired
	private PasswordEncoder passwordEncoder;	//aula 23.15
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		// return usuarioRepository.save(usuario);  / coment: aula 12.11
		usuarioRepository.detach(usuario);
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());  
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		if (usuario.isNovo()) {		//aula 23.15
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {		//aula 23.15
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}		
		usuario.setSenha(passwordEncoder.encode(novaSenha));
	}
		
/*			if (usuario.senhaNaoCoincideCom(senhaAtual)) {
				throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
			}		
		usuario.setSenha(novaSenha);
	}...................................................................condição alterada na aula 23.15
/*...................................aula 12.16...............................................*/
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		usuario.removerGrupo(grupo);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		usuario.adicionarGrupo(grupo);
	}
/*..........................................................aula 12.16........................*/
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}	
}