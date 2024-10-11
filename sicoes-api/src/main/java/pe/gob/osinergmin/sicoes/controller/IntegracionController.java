package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gob.osinergmin.sne.domain.dto.rest.out.PidoOutRO;
import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.util.bean.PidoBeanOutRO;
import pe.gob.osinergmin.sicoes.util.bean.SuneduOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UnidadOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UsuarioOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionOutRO;

@RestController
@RequestMapping("/api/pido")
public class IntegracionController extends BaseRestController{

	private Logger logger = LogManager.getLogger(IntegracionController.class);
	

	@Autowired
	SneApiConsumer sneApiConsumer; 
	
	@Autowired
	PidoConsumer pidoConsumer;
	
	@GetMapping("/sunat/{ruc}")
	public PidoBeanOutRO consultaRUC(@PathVariable String ruc)throws Exception{
		logger.info("consultaRUC {} ",ruc);
		return pidoConsumer.obtenerContribuyente(ruc);
	}
	
	@GetMapping("/reniec/{DNI}")
	public PidoBeanOutRO consultaDNI(@PathVariable String DNI)throws Exception{
		logger.info("consultaDNI {} ",DNI);
		return pidoConsumer.obtenerPidoCiudadanoOrquestado(DNI);
	}
	
	@GetMapping("/migracion/{NRO}")
	//public PidoBeanOutRO consultaCE(@PathVariable String NRO)throws Exception{
	public PidoOutRO consultaCE(@PathVariable String NRO)throws Exception{
		logger.info("consultaCE {} ",NRO);
		//return pidoConsumer.obtenerCiudadnoExtranjero(NRO);
		return pidoConsumer.consultarPidoMigraccionesCiudadanoExtranjero(NRO);
	}
	
	@GetMapping("/sunedu/{NRO}")
	public SuneduOutRO consultaGrados(@PathVariable String NRO)throws Exception{
		logger.info("consultaGrados {} ",NRO);
		return pidoConsumer.obtenerGrados(NRO);
	}
	
	@GetMapping("/SNE")
	public AfiliacionOutRO consultaSNE(@RequestParam String nroDocumento,@RequestParam Integer tipoDocumento)throws Exception{
		logger.info("consultaPartidas {} ",nroDocumento);
		String token =sneApiConsumer.obtenerTokenAcceso();
		AfiliacionBeanDTO afiliacionBeanDTO=new AfiliacionBeanDTO();
		afiliacionBeanDTO.setTipoDocumento(tipoDocumento);
		afiliacionBeanDTO.setNumeroDocumento(nroDocumento);
		afiliacionBeanDTO.setAppInvoker("vvo");
		afiliacionBeanDTO.setVerificarData(true);
		return sneApiConsumer.obtenerAfiliacion(afiliacionBeanDTO, token);
	}
	
	@GetMapping("/listar-unidad")
	public List<UnidadOutRO> listarUnidad(){
		logger.info("listarUnidad  ");
		return pidoConsumer.listarUnidad();
	}
	
	@GetMapping("/listar-usuarios")
	public List<UsuarioOutRO> listarUsuario(){
		logger.info("listarUsuarios ");
		return pidoConsumer.listarUsuarios();
	}
}
