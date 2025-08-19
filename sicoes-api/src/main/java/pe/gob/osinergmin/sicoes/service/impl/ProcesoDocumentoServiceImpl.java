package pe.gob.osinergmin.sicoes.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoDocumento;
import pe.gob.osinergmin.sicoes.repository.ProcesoDocumentoDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoDocumentoService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ProcesoDocumentoServiceImpl implements ProcesoDocumentoService {

	@Autowired
	ProcesoDocumentoDao procesoDocumentoDao;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private ProcesoService procesoService;

	@Autowired
	private ArchivoService archivoService;

	@Override
	public ProcesoDocumento guardar(ProcesoDocumento model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcesoDocumento obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		ProcesoDocumento pDoc = procesoDocumentoDao.obtener(id);
		Long archivoId = pDoc.getArchivo().getIdArchivo();
		procesoDocumentoDao.delete(pDoc);
		archivoService.eliminar(archivoId, contexto);
	}

	@Override
	public Page<ProcesoDocumento> listarDocumentos(Long idProceso, Pageable pageable, Contexto contexto) {
		ListadoDetalle ldArchivoFormulacionConsultas = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO, Constantes.LISTADO.TIPO_ARCHIVO.INFORMACION_FORMULACION_CONSULTAS);
		Page<ProcesoDocumento> page=procesoDocumentoDao.buscar(idProceso, ldArchivoFormulacionConsultas.getIdListadoDetalle(), pageable);
		return page;
	}

	@Override
	public ProcesoDocumento registrar(MultipartFile file, Long idEtapa, Long idProceso, String documentName,
			Contexto contexto) {
		if (file == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_SUBIR_ARCHIVO);
		}
		if (idProceso == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_NO_ENVIADO);
		}
		if (idEtapa == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ETAPA_NO_ENVIADA);
		}

		ListadoDetalle etapaAbsolucion = listadoDetalleService.obtenerListadoDetalleOrden(Constantes.LISTADO.ETAPA_PROCESO.CODIGO,
				Constantes.LISTADO.ETAPA_PROCESO.ETAPA_ABSOLUCION_ORDEN);

		boolean subirASiged = etapaAbsolucion.getIdListadoDetalle().equals(idEtapa);

		Archivo archivo = new Archivo();
		archivo.setFile(file);

		ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.INFORMACION_PROCESO);
		archivo.setTipoArchivo(tipoArchivo);
		archivo.setDescripcion(documentName);
		archivo.setIdProceso(idProceso);

		Archivo archivoSave = subirASiged
				? archivoService.guardarEnSiged(idProceso, archivo, contexto)
				: archivoService.guardar(archivo, contexto);

		return guardarProcesoDocumento(idProceso, contexto, archivoSave, documentName, tipoArchivo, idEtapa, true);
	}

	@Override
	public ProcesoDocumento registrarXls(MultipartFile file, Long idEtapa, Long idProceso, String documentName,
										 Contexto contexto) {
		if (file == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_SUBIR_ARCHIVO);
		}
		if (idProceso == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_NO_ENVIADO);
		}
		if (idEtapa == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ETAPA_NO_ENVIADA);
		}

		Archivo archivo = new Archivo();
		archivo.setFile(file);
		ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.INFORMACION_FORMULACION_CONSULTAS);
		archivo.setTipoArchivo(tipoArchivo);
		archivo.setDescripcion(documentName);
		archivo.setIdProceso(idProceso);

		Archivo archivoSave = archivoService.guardarExcelEnSiged(idProceso, archivo, contexto);

		return guardarProcesoDocumento(idProceso, contexto, archivoSave, documentName, tipoArchivo, idEtapa, false);
	}

	private ProcesoDocumento guardarProcesoDocumento(Long idProceso, Contexto contexto, Archivo archivoSave, String documentName, ListadoDetalle tipoArchivo, Long idEtapa, boolean isManual) {
		ListadoDetalle ld = !isManual
				? listadoDetalleService.obtener(tipoArchivo.getIdListadoDetalle(), contexto)
				: listadoDetalleService.obtener(idEtapa, contexto);

		Proceso process = procesoService.obtener(idProceso, contexto);
		ProcesoDocumento pd = new ProcesoDocumento();
		pd.setFechaPublicacion(LocalDateTime.now());
		pd.setArchivo(archivoSave);
		pd.setDocumentName(documentName);
		pd.setEtapa(ld);
		pd.setProceso(process);
		AuditoriaUtil.setAuditoriaRegistro(pd, contexto);
		procesoDocumentoDao.save(pd);
		return pd;
	}

}
