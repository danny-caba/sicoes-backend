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
import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.repository.ProcesoMiembroDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoMiembroService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ProcesoMiembroServiceImpl implements ProcesoMiembroService {

	Logger logger = LogManager.getLogger(ProcesoMiembroServiceImpl.class);

	@Autowired
	private ProcesoMiembroDao procesoMiembroDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Override
	public void eliminar(Long idProcesoItem, Contexto contexto) {
		procesoMiembroDao.deleteById(idProcesoItem);		
	}

	@Override
	public Page<ProcesoMiembro> listarMiembros(String uuidProceso,Pageable pageable, Contexto contexto) {
		Page<ProcesoMiembro> page=procesoMiembroDao.buscar(uuidProceso,pageable);
		return page;
	}  

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcesoMiembro guardar(ProcesoMiembro miembro, Contexto contexto) {
		ProcesoMiembro miembroBD=null;
		if(miembro.getCargo()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.CARGO_NO_ENVIADO);
		}
		if(miembro.getCodigoUsuario()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ENVIADO);
		}
		if(miembro.getNombreUsuario()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ENVIADO);
		}
		List<ProcesoMiembro> miembros = procesoMiembroDao.buscar(miembro.getProceso().getIdProceso());
		ListadoDetalle cargoActual = listadoDetalleService.obtener(miembro.getCargo().getIdListadoDetalle(), contexto);
		for(ProcesoMiembro otroMiembro:miembros) {
			ListadoDetalle cargoBuscado=listadoDetalleService.obtener(otroMiembro.getCargo().getIdListadoDetalle(), contexto);
			if(cargoActual.equals(cargoBuscado)) {
				if(Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO.equals(otroMiembro.getEstado().getCodigo())
						&&Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE.equals(cargoActual.getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_CARGO_PRESIDENTE);
				}
				if(Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO.equals(otroMiembro.getEstado().getCodigo())
						&&Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER.equals(cargoActual.getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_CARGO_TITULAR);
				}
				if(Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO.equals(otroMiembro.getEstado().getCodigo())
						&&Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO.equals(cargoActual.getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_CARGO_TITULAR_3);
				}
			}
			if(Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO.equals(otroMiembro.getEstado().getCodigo())
					&&otroMiembro.getCodigoUsuario().equals(miembro.getCodigoUsuario())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.REGISTRO_EXISTENTE_USUARIO);
			}
		}
		if(miembro.getIdProcesoMiembro()==null) {
			ListadoDetalle estadoActivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_MIEMBRO.CODIGO, Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO);
			miembroBD=miembro;
			miembroBD.setEstado(estadoActivo);
			miembroBD.setFechaRegistro(new Date());
		}else {
			miembroBD = procesoMiembroDao.obtener(miembro.getIdProcesoMiembro(),miembro.getProceso().getProcesoUuid());
			miembroBD.setCargo(miembro.getCargo());
			miembroBD.setCodigoUsuario(miembro.getCodigoUsuario());
			miembroBD.setNombreUsuario(miembro.getNombreUsuario());
		}
		AuditoriaUtil.setAuditoriaRegistro(miembroBD,contexto);
		procesoMiembroDao.save(miembroBD);
		return miembroBD;
	}

	@Override
	public ProcesoMiembro obtener(Long id,String uuidProceso, Contexto contexto) {
		return procesoMiembroDao.obtener(id,uuidProceso);
	}

	@Override
	public ProcesoMiembro obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminarMiembro(Long id, String uuidProceso, Contexto contexto) {
		ProcesoMiembro procesoMiembro = procesoMiembroDao.obtener(id, uuidProceso);
		procesoMiembroDao.delete(procesoMiembro);		
	}

	@Override
	public ProcesoMiembro obtenerXtipo(String procesoUuid, String tipoCargo, Contexto contexto) {
		return procesoMiembroDao.obtener(procesoUuid,tipoCargo);
	}
	
	@Override
	public ProcesoMiembro obtenerXUsuario(String procesoUuid,Long codigoUsuario, Contexto contexto) {
		return procesoMiembroDao.obtener(procesoUuid,codigoUsuario);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcesoMiembro inactivar(ProcesoMiembro miembro, Contexto contexto) {
		ProcesoMiembro miembroBD = procesoMiembroDao.obtener(miembro.getIdProcesoMiembro(), miembro.getProceso().getProcesoUuid());
		if(!(miembroBD == null)) {
			ListadoDetalle estadoInactivado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_MIEMBRO.CODIGO, Constantes.LISTADO.ESTADO_MIEMBRO.INACTIVO);
			miembroBD.setEstado(estadoInactivado);
		}
		AuditoriaUtil.setAuditoriaRegistro(miembroBD,contexto);
		procesoMiembroDao.save(miembroBD);
		return miembroBD;
	}



	

}
