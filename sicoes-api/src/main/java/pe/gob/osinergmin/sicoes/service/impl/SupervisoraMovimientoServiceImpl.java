package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.repository.SupervisoraMovimientoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class SupervisoraMovimientoServiceImpl implements SupervisoraMovimientoService {

	Logger logger = LogManager.getLogger(SupervisoraMovimientoServiceImpl.class);

	@Autowired
	private SupervisoraMovimientoDao supervisoraMovimientoDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	

	@Override
	public SupervisoraMovimiento obtener(Long id, Contexto contexto) {
		return supervisoraMovimientoDao.obtener(id);
	}
	
	@Override
	public SupervisoraMovimiento guardar(SupervisoraMovimiento supervisoraMovimiento, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(supervisoraMovimiento,contexto);
		return supervisoraMovimientoDao.save(supervisoraMovimiento);
	}
	
	@Override
	public SupervisoraMovimiento desbloquear(SupervisoraMovimiento supervisoraMovimiento, Contexto contexto) {
		
		SupervisoraMovimiento supervisoraMovimientoBD = new SupervisoraMovimiento();
		supervisoraMovimientoBD = supervisoraMovimiento;
		supervisoraMovimientoBD.setIdSupervisoraMovimiento(null);
		supervisoraMovimientoBD.setMotivo(null);
		supervisoraMovimientoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
		supervisoraMovimientoBD.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.MANUAL));
		supervisoraMovimientoBD.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.DESBLOQUEO));
		supervisoraMovimientoBD.setFechaRegistro(new Date());
		AuditoriaUtil.setAuditoriaRegistro(supervisoraMovimientoBD,contexto);
		
		return supervisoraMovimientoDao.save(supervisoraMovimientoBD);
	}

	@Override
	public Page<SupervisoraMovimiento> listarMovimientos(Long idSector, Long idSubsector, Long idEstadoItem, String item, String nombreProceso, String ruc, Pageable pageable, Contexto contexto){
		return supervisoraMovimientoDao.listarMovimientos(idSector,idSubsector,idEstadoItem,item,nombreProceso,ruc,pageable);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		
	}

	@Override
	public Page<SupervisoraMovimiento> listarHistorial(Long idSupervisora, Long idSector, Long idSubsector, Pageable pageable,
			Contexto contexto){
		return supervisoraMovimientoDao.listarHistorial(idSupervisora,idSector,idSubsector,pageable);
	}

	@Override
	public List<SupervisoraMovimiento> listarXProfesional(Long idSupervisora, Long idSubsector) {
		return supervisoraMovimientoDao.listarXProfesional(idSupervisora,idSubsector);
	}

	@Override
	public SupervisoraMovimiento ultimoMovimientoXProfesional(Long idSupervisora, Long idSubsector,Long idEstado,Contexto contexto) {
		List<SupervisoraMovimiento> movimientos = supervisoraMovimientoDao.listarUltimoMovimiento(idSupervisora,idSubsector);
		
		if(!movimientos.isEmpty() ) {
			ListadoDetalle estadoMovimiento = listadoDetalleService.obtener(idEstado, contexto);
			SupervisoraMovimiento ultimoMovimiento =  movimientos.get(0);
			if(estadoMovimiento == null) {
				return ultimoMovimiento;
			}else {
				if(estadoMovimiento.getCodigo().equals(ultimoMovimiento.getEstado().getCodigo())) {
					return ultimoMovimiento;
				}
			}
			return null;
		}else {
			SupervisoraMovimiento sup = new SupervisoraMovimiento();
			sup.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
			return sup;
		}
	}
	
	@Override
	public List<SupervisoraMovimiento> listarXProfesionalXItem(Long idSupervisora, Long idSubsector) {
		return supervisoraMovimientoDao.listarXProfesionalXItem(idSupervisora,idSubsector);
	}

	
}
