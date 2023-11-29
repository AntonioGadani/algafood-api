package com.algaworks.algafood.api.v1.controller;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/estatisticas")	//antes: @RequestMapping(path = "/estatisticas")	//aula 20.14
public class EstatisticasController {	//aula 13.13(Estatísticas), 13.19, 19.37, 23.35
	@Autowired
	private VendaQueryService vendaQueryService;
	@Autowired
	private VendaReportService vendaReportService;		//aula 13.19
	@Autowired
	private AlgaLinks algaLinks;		//aula 19.37

	@CheckSecurity.Estatisticas.PodeConsultar	//aula 23.35
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
		var estatisticasModel = new EstatisticasModel();
		estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
		return estatisticasModel;
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar	//aula 23.35
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar	//aula 23.35
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
	                   @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}
}
	//para este caso, não usamos classes DTO, pois é um classe simples só de consulta	
	//@GetMapping("/vendas-diarias").........coment: aula 13.19	
/*coment: aula 13.16 e 19.37
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		return vendaQueryService.consultarVendasDiarias(filtro);
...............................................................................................coment: aula 23.16..
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)   //aula 13.19
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			//Não é obrigado especificar; se não especificar timeOffset, usa o padrão UTC: "+00:00"
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
}
*...............................................................................abaixo, aula 13.19.......
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
													//"attachment: só para download. Não exibido online
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	//.........................................................................acima, aula 13.19 e 19.37.......*/	
