package com.algaworks.algafood.domain.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
//import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {	//aulas 12.22, 12.23, 15.4, 15.5, 15.12, 15.14

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	//@Autowired
	//private EnvioEmailService envioEmail;		aula 15.11
	
	@Autowired
	private PedidoRepository pedidoRepository;		//aula 15.11
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);		//aula 15.12
	}
		
/*		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				//.corpo("O pedido de código <strong>"+ pedido.getCodigo() + "</strong> foi confirmado!")
				.corpo("pedido-confirmado.html")	//aula 15.5
				.variavel("pedido", pedido)			//aula 15.5		
				.destinatario(pedido.getCliente().getEmail())
				.build();
		envioEmail.enviar(mensagem);  
	}							.................esse trecho será utilizado em outra classe:  aula 15.11*/
		
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		 pedidoRepository.save(pedido);		//aula 15.14
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
}