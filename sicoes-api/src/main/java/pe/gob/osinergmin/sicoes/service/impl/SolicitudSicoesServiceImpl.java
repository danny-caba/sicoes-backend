package pe.gob.osinergmin.sicoes.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.consumer.SissegApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.HistorialAprobador;
import pe.gob.osinergmin.sicoes.model.HistorialVacaciones;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.DetalleVacacionesDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPendienteDTO;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.AsignacionPerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.EvaluacionPendienteDao;
import pe.gob.osinergmin.sicoes.repository.HistorialAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.HistorialVacacionesDao;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.PerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.service.SolicitudSicoesService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;


@Service
public class SolicitudSicoesServiceImpl implements SolicitudSicoesService{
	
	Logger logger = LogManager.getLogger(SolicitudSicoesServiceImpl.class);

	@Autowired
	private SicoesSolicitudDao sicoesSolicitudDao;

	@Override
	public SicoesSolicitud guardar(SicoesSolicitud model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SicoesSolicitud obtener(Long idSolicitud, Contexto contexto) {
		// TODO Auto-generated method stub
		return sicoesSolicitudDao.findById(idSolicitud).get();
	}
	@Override
	public SicoesSolicitud updateSolicitud(String estado, SicoesSolicitud sicoesSolicitud, Contexto contexto) {
	 
		 
			Optional<SicoesSolicitud> soliciyudOpt = sicoesSolicitudDao.findById(sicoesSolicitud.getIdSolicitud());
			SicoesSolicitud sicoesSolicitudFinal=soliciyudOpt.orElseThrow(() -> new RuntimeException("sicoesSolicitud  no encontrado"));
			sicoesSolicitudFinal.setFecActualizacion(new Date());
			 if (estado != null) {
				 sicoesSolicitudFinal.setEstadoProcesoSolicitud(estado );
	          }
			
			 if (sicoesSolicitud.getDescripcionSolicitud() != null) {
				 sicoesSolicitudFinal.setDescripcionSolicitud(sicoesSolicitud.getDescripcionSolicitud());
	          }
			 if (sicoesSolicitud.getEstado()  != null) {
				 sicoesSolicitudFinal.setEstado(sicoesSolicitud.getEstado());
	          }
			 if (sicoesSolicitud.getTipoSolicitud()!= null) {
				 sicoesSolicitudFinal.setTipoSolicitud(sicoesSolicitud.getTipoSolicitud()); 
	          }
			 
			 sicoesSolicitudFinal.setIpActualizacion(contexto.getIp());
			 sicoesSolicitudFinal.setUsuActualizacion(contexto.getUsuario().getUsuario());
			return  sicoesSolicitudDao.save(sicoesSolicitudFinal) ;
		 
	}

	@Override
	public Optional<SicoesSolicitud>  getSolicitud(Long id, Contexto contexto) {
		 
		return sicoesSolicitudDao.findById(id);
	}
 

	@Override
	public Optional<SicoesSolicitud> putRequisitoFinaliza(SicoesSolicitud sicoesSolicitud, Contexto contexto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	 
}
