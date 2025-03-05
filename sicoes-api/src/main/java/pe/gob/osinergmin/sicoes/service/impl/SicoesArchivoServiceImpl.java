package pe.gob.osinergmin.sicoes.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfReader;

import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.SicoesArchivo;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.service.SicoesArchivoService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class SicoesArchivoServiceImpl implements SicoesArchivoService {
	
	Logger logger = LogManager.getLogger(SicoesArchivoServiceImpl.class);
	
	@Autowired
	private SigedOldConsumer sigedOldConsumer;

	@Override
	public SicoesArchivo guardarEnAlfresco(SicoesArchivo archivo, Contexto contexto) {
		
		Archivo archivoToAlfresco = new Archivo();
		
		Integer tamanioByte=null;
		Double tamanioMB=null;
		try {
			if(archivo.getFile()!=null) {
				tamanioByte=archivo.getFile().getBytes().length;	
			}else {
				tamanioByte=archivo.getContenido().length;
			}
			tamanioMB=tamanioByte/(1024.0*1024.0);
			logger.info("tamanio bytes : "+tamanioByte);
			logger.info("tamanio : "+tamanioMB);			
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
		
		if (archivo.getFile() != null) {
			archivoToAlfresco.setNombreReal(reemplazarCaracteres(archivo.getFile().getOriginalFilename()));
			archivoToAlfresco.setTipo(archivo.getFile().getContentType());
			/*if(archivo.getIdPropuesta() != null&&(archivo.getIdPropuestaEconomica()!=null||archivo.getIdPropuestaTecnica()!=null)) {
				SimpleDateFormat sdf2=new SimpleDateFormat("hhmmss");
				String hora = sdf2.format(new Date());
				String nombre = reemplazarCaracteres(archivo.getNombreReal());
				nombre=nombre.replace(".pdf", "");
				archivo.setNombreReal(nombre+"-"+hora+".pdf");
			}*/
			PdfReader reader;
			try {
				if ("application/pdf".equals(archivo.getFile().getContentType())) {
					reader = new PdfReader(archivo.getFile().getBytes());
					int count = reader.getNumberOfPages();
					archivoToAlfresco.setNroFolio(count * 1L);
					archivoToAlfresco.setPeso(archivo.getFile().getSize());
				} else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(archivo.getFile().getContentType()) ||
						"application/vnd.ms-excel".equals(archivo.getFile().getContentType())) {
					archivoToAlfresco.setNroFolio(1L);
					archivoToAlfresco.setPeso(archivo.getFile().getSize());
				}
			} catch (Exception e) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_SE_PUEDE_LEER);
			}

		}
		
		if (archivo.getFile() != null || archivo.getContenido() != null) {
			
			archivoToAlfresco.setFile(archivo.getFile());
			archivoToAlfresco.setIdSolicitud(archivo.getIdSolicitud());
			String nombre = sigedOldConsumer.subirArchivosAlfresco(archivo.getIdSolicitud(),null,null,null, archivoToAlfresco);
			archivoToAlfresco.setFile(null);
			archivo.setNombreAlFresco(nombre);
		}
		
		return archivo;
	}

	@Override
	public void eliminarIdSicoesArchivo(Long id, Contexto contexto) {
		// DEFINIR METODO PARA ELIMINAR EN ALFRESCO!!
		
	}
	
	private String reemplazarCaracteres(String originalFilename) {
		if (originalFilename == null) {
			throw new NullPointerException("originalFilename es nulo");
		}
		return originalFilename.replaceAll("[^\\p{ASCII}]", "");
	}

}
