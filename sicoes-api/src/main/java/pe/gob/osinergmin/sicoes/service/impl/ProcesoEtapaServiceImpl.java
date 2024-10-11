package pe.gob.osinergmin.sicoes.service.impl;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.repository.ProcesoEtapaDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ProcesoEtapaServiceImpl implements ProcesoEtapaService {

	Logger logger = LogManager.getLogger(ProcesoEtapaServiceImpl.class);

	@Autowired
	private ProcesoEtapaDao procesoEtapaDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@Override
	public void eliminar(Long idProcesoEtapa, Contexto contexto) {
		procesoEtapaDao.deleteById(idProcesoEtapa);		
	}

	@Override
	public Page<ProcesoEtapa> listarEtapas(String procesoUuid,Pageable pageable, Contexto contexto) {
		Page<ProcesoEtapa> page=procesoEtapaDao.buscar(procesoUuid,pageable);
		return page;
	}  

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcesoEtapa guardar(ProcesoEtapa etapa, Contexto contexto) {
		ProcesoEtapa etapaBD=null;
		if(etapa.getProceso()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_NO_ENVIADO);
		}
		Proceso proceso = procesoService.obtener(etapa.getProceso().getProcesoUuid(), contexto);
		if(etapa.getEtapa()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ETAPA_NO_ENVIADA);
		}
		if(etapa.getFechaFin()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_FIN_NO_ENVIADO);
		}
		if(DateUtil.esMayor(etapa.getFechaInicio(),etapa.getFechaFin())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_MAYOR_IGUAL);
		}
		ListadoDetalle etapaActual=listadoDetalleService.obtener(etapa.getEtapa().getIdListadoDetalle(), contexto);
		
		if(etapa.getIdProcesoEtapa()==null) {
			if(!Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(proceso.getEstado().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.AGREGAR_ELABORACION);
			}
			List<ProcesoEtapa> etapas =procesoEtapaDao.buscar(etapa.getProceso().getProcesoUuid());
			for(ProcesoEtapa otraEtapa:etapas) {
				ListadoDetalle etapaBuscada=listadoDetalleService.obtener(otraEtapa.getEtapa().getIdListadoDetalle(), contexto);
				if(etapaActual.getCodigo().equals(etapaBuscada.getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.REGISTRO_EXISTENTE_ETAPA);
				}
			}
			if(DateUtil.esMenorIgual(etapa.getFechaInicio(), new Date())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_MINIMA_MENOR);
			}
			if(etapa.getFechaInicio()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_NO_ENVIADO);
			}
			etapaBD=etapa;
			etapaBD.setProceso(proceso);
		} else {
			if((Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA.equals(proceso.getEstado().getCodigo())
					||Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(proceso.getEstado().getCodigo()))) {
				if(DateUtil.esMenorIgual(etapa.getFechaInicio(), new Date())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_MINIMA_MENOR);
				}
			}
			
			if (!(Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(proceso.getEstado().getCodigo())
					|| Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA.equals(proceso.getEstado().getCodigo())
					||Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION.equals(proceso.getEstado().getCodigo()))) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.EDITAR_ELABORACION);
			}

			if (Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA.equals(proceso.getEstado().getCodigo())) {
				if (DateUtil.esMenorIgual(etapa.getFechaInicio(), new Date())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.F_INICIO_CONVOCATORIA);
				}
				if (DateUtil.esMenorIgual(etapa.getFechaFin(), new Date())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.F_FIN_CONVOCATORIA);
				}
			}

			if (Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION.equals(proceso.getEstado().getCodigo())) {
				if (DateUtil.esMenorIgual(etapa.getFechaFin(), new Date())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.F_FIN_PRESENTACION);
				}
			}
			
			etapaBD = procesoEtapaDao.obtener(etapa.getIdProcesoEtapa(), etapa.getProceso().getProcesoUuid());
			
			if (!(Constantes.LISTADO.ESTADO_PROCESO.ADMISION_CALIFICACION.equals(proceso.getEstado().getCodigo())||
					Constantes.LISTADO.ESTADO_PROCESO.DESIGNACION.equals(proceso.getEstado().getCodigo())||
					Constantes.LISTADO.ESTADO_PROCESO.CERRADO.equals(proceso.getEstado().getCodigo()))||
					Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION.equals(proceso.getEstado().getCodigo())) {
				etapaBD.setFechaInicio(etapa.getFechaInicio());
			}
			if(!(Constantes.LISTADO.ESTADO_PROCESO.ADMISION_CALIFICACION.equals(proceso.getEstado().getCodigo())||
					Constantes.LISTADO.ESTADO_PROCESO.DESIGNACION.equals(proceso.getEstado().getCodigo())||
					Constantes.LISTADO.ESTADO_PROCESO.CERRADO.equals(proceso.getEstado().getCodigo()))) {
				etapaBD.setFechaFin(etapa.getFechaFin());
			}
			
		}
		AuditoriaUtil.setAuditoriaRegistro(etapaBD,contexto);
		procesoEtapaDao.save(etapaBD);
		return etapaBD;
	}

	@Override
	public ProcesoEtapa obtener(Long id,String uuidProceso, Contexto contexto) {
		return procesoEtapaDao.obtener(id,uuidProceso);
	}

	@Override
	public ProcesoEtapa obtener(Long id, Contexto contexto) {
		return procesoEtapaDao.obtener(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, String uuidProceso, Contexto contexto) {
		ProcesoEtapa procesoEtapa = procesoEtapaDao.obtener(id, uuidProceso);
		procesoEtapaDao.delete(procesoEtapa);
	}

	@Override
	public List<ProcesoEtapa> listar(String procesoUuid, Contexto contexto) {
		return  procesoEtapaDao.buscar(procesoUuid);
	}

}
