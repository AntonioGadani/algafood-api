package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;  aula 12.17
//import org.springframework.dao.EmptyResultDataAccessException;   aula 12.17
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;    aula 12.17
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {	/*4.29, 4.30, 5.4, 55.5, 8.6, 8.11, 11.1, 12.4, 12.7, 
                                              12.12, 12.14, 12.17 */
/*	private static final String MSG_RESTAURANTE_EM_USO = 
 * "RESTAURANTE de código %d não pode ser removida, pois está em uso";         aula 12.17 */

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;		//aula 12.7
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;	//aula 12.12
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;				//aula 12.17
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		//abaixo, dentro da Entidade Cozinha, pega o id da cozinha
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();  /*aula 12.7, igual cozinha 
		 																  linha anterior, acima*/
		//abaixo, na classe Cozinha, verifica se a cozinha existe usando o método buscarOuFalhar
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);	//aula 12.7, igual cozinha
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);//aula 12.7, igual cozinha, linha anterior
		
		return restauranteRepository.save(restaurante);
	}
/*	Código "excluir"  Desapareceu na aula 12.17
	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();	//aula 11.21; Libera todas as alterações pendentes no banco de dados
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);		//Se não encontrar cidade, lança exception

		
		} catch (DataIntegrityViolationException e) {	//Se a cidade estiver em uso, lança exception
			throw new EntidadeEmUsoException(
				String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}																			
	}																		*/
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();
	}
/*...................................................aula 12.18...............................*/
	@Transactional
	public void ativar(List<Long> restauranteIds) {	//recebendo uma lista de restaurantes p/ativar
		restauranteIds.forEach(this::ativar);		//ativar todos os restaurantes da lista
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {//recebendo lista de restaurantes p/inativar
		restauranteIds.forEach(this::inativar);		 //Inativar todos os restaurantes da lista
	}
/*............fim aula 12.18.................................................................*/
/*.................................aula 12.14................................................*/
	@Transactional
	public void abrir(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.fechar();
	}   
	/*.............................................................aula 12.14.....................*/	
/*....métodos incluídos na aula 12.12...............................................................*/	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		//Acima:tratando "id" de FormaPagamento. Se não existir, chama exception
		restaurante.removerFormaPagamento(formaPagamento); //Remove FormaPgto de Restaurante
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		//abaixo: verifica se existe uma formaPagamento(id)
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		//acima: se não existe, lança exception.
		//abaixo: Se existe formaPagamento(id), chama método adicionarFormaPgto
		restaurante.adicionarFormaPagamento(formaPagamento);
	}      /* ..............................................Fim das inclusões na aula 12.12...*/	
/*........................aula 12.17..........................................................*/
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
/*........................aula 12.17..........................................................*/
	public Restaurante buscarOuFalhar(Long restauranteId) {//busca a entidade Restaurante
		return restauranteRepository.findById(restauranteId)  //Se encontrar, informa o id
				//abaixo, //Se não encontrar, lança uma exception
			.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}