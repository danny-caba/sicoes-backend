package pe.gob.osinergmin.sicoes.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;

public class GenerarBatchMasivo implements Runnable {
	
	private Logger logger = LogManager.getLogger(GenerarBatchMasivo.class);
	
	
	ProcesoItemService procesoItemService;
	ProcesoItem procesoItem;
	Contexto contexto;
	
	public GenerarBatchMasivo(ProcesoItemService procesoItemService,ProcesoItem procesoItem,Contexto contexto) {
		super();
		this.procesoItemService = procesoItemService;
		this.procesoItem = procesoItem;
		this.contexto=contexto;
	}


	public void run() {
		logger.info("Inicio el proceso GenerarBatchMasivo");
		procesoItemService.generarArchivoDescarga(procesoItem,contexto);
//		try {
//			Thread.sleep(10*1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		logger.info("Fin el proceso GenerarBatchMasivo");
	}

}
