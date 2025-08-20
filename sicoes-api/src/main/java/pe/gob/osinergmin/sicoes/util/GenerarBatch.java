package pe.gob.osinergmin.sicoes.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.service.PropuestaService;

public class GenerarBatch implements Runnable {
	
	private Logger logger = LogManager.getLogger(GenerarBatch.class);
	
	PropuestaService propuestaService;
	Propuesta propuesta;
	Contexto contexto;
	
	public GenerarBatch(PropuestaService propuestaService,Propuesta propuesta,Contexto contexto) {
		super();
		this.propuestaService = propuestaService;
		this.propuesta=propuesta;
		this.contexto=contexto;
	}

	public void run() {
		logger.info("Inicio el proceso GenerarBatch");
		propuestaService.generarArchivoDescarga(propuesta,contexto);
//		try {
//			Thread.sleep(10*1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		logger.info("fin el proceso GenerarBatch");
	}

}
