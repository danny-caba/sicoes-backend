package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.ContratoDetalleDTO;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.ContratoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ContratoServiceImpl implements ContratoService {
	Logger logger = LogManager.getLogger(ContratoServiceImpl.class);

	@Autowired
	private ContratoDao contratoDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private SicoesSolicitudDao sicoesSolicitudDao;
	
	@Autowired
	private AsignacionDao asignacionDao;

	@Override
	public Contrato guardar(Contrato contrato, Contexto contexto) {
		return contratoDao.save(contrato);
	}

	@Override
	public Contrato obtener(Long idContrato, Contexto contexto) {
		return contratoDao.findById(idContrato).orElse(null);
	}

	@Override
	public void eliminar(Long idContrato, Contexto contexto) {
		contratoDao.deleteById(idContrato);
	}

	@Override
	public Contrato registrarNuevoContrato(SicoesSolicitud solicitud, Contexto contexto) throws Exception {
		Contrato contrato = new Contrato();
		contrato.setSolicitudPerfCont(solicitud);
		contrato.setEstadoContrato("1");
		AuditoriaUtil.setAuditoriaRegistro(contrato, contexto);
		return contratoDao.save(contrato);
	}

	@Override
	public Page<Contrato> obtenerContratos(String expediente, String contratista, String tipoContrato, String areaSolicitante, Pageable pageable, Contexto contexto) {
		return contratoDao.obtenerContratos(expediente, contratista, tipoContrato, areaSolicitante, pageable);
	}

	@Override
    @Transactional(readOnly = true)
	public Optional<ContratoDetalleDTO> obtenerContratoDetallePorId(Long idContrato) {
        Optional<Contrato> contratoOptional = contratoDao.findById(idContrato);
        if (contratoOptional.isPresent()) {
            Contrato contrato = contratoOptional.get();
            return Optional.of(mapToContratoFormDTO(contrato));
        }
        return Optional.empty();
	}

	@Override
	@Transactional
	public ContratoDetalleDTO actualizarContratoDetalle(ContratoDetalleDTO contratoActualizadoDto, Contexto contexto) {
	    if (contratoActualizadoDto.getIdContrato() == null) {
	        throw new IllegalArgumentException("El ID del contrato es obligatorio para la actualización.");
	    }

	    Contrato existeContrato = contratoDao.findById(contratoActualizadoDto.getIdContrato())
	            .orElseThrow(() -> new RuntimeException("Contrato no encontrado con ID: " + contratoActualizadoDto.getIdContrato()));

	    if (contratoActualizadoDto.getNumeroContratoSap() != null && !contratoActualizadoDto.getNumeroContratoSap().isEmpty()) {
	        try {
	            existeContrato.setNumeroContrato(Long.valueOf(contratoActualizadoDto.getNumeroContratoSap()));
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("El número de contrato SAP ('" + contratoActualizadoDto.getNumeroContratoSap() + "') debe ser un número válido.", e);
	        }
	    } else {
	        existeContrato.setNumeroContrato(null);
	    }

	    if (existeContrato.getFechaSuscripcionContrato() == null) {
	        if (contratoActualizadoDto.getFechaSuscripcionContrato() == null) {
	            throw new IllegalArgumentException("La fecha de suscripción es obligatoria para la primera actualización.");
	        }

	        existeContrato.setFechaSuscripcionContrato(contratoActualizadoDto.getFechaSuscripcionContrato());

	        ListadoDetalle estadoRevisionProceso = listadoDetalleService.obtenerListadoDetalle(
	                Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.EN_PROCESO);
	        existeContrato.setEstado(estadoRevisionProceso);
	        logger.info("Contrato ID: " + existeContrato.getIdContrato() + " - Fecha suscripción agregada. Cambiando estado a EN PROCESO.");

	    } else {
	        existeContrato.setFechaSuscripcionContrato(contratoActualizadoDto.getFechaSuscripcionContrato());

	        if (contratoActualizadoDto.getFechaInicioContrato() == null || contratoActualizadoDto.getFechaFinalContrato() == null) {
	            throw new IllegalArgumentException("Las fechas de inicio y final del contrato son obligatorias para esta actualización.");
	        }

	        if (contratoActualizadoDto.getFechaFinalContrato().compareTo(contratoActualizadoDto.getFechaInicioContrato()) <= 0) {
	            throw new IllegalArgumentException("La fecha final del contrato debe ser mayor que la fecha de inicio.");
	        }
	        
	        if (existeContrato.getFechaInicioContrato() == null && existeContrato.getFechaFinalContrato() == null) {
		        ListadoDetalle estadoRevisionConcluido = listadoDetalleService.obtenerListadoDetalle(
		                Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.CONCLUIDO);
		        existeContrato.setEstado(estadoRevisionConcluido);
		        logger.info("Contrato ID: " + existeContrato.getIdContrato() + " - Fechas inicio y fin agregadas. Cambiando estado a CONCLUIDO.");
		        ListadoDetalle estadoContratoAsignado = listadoDetalleService.obtenerListadoDetalle(
		                Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.EN_PROCESO);
		        existeContrato.setIdEstadoEval(estadoContratoAsignado);
		        
		        ListadoDetalle estadoAsignadoLogistica = listadoDetalleService.obtenerListadoDetalle(
		                Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
		        existeContrato.setEstadoEvalLog(estadoAsignadoLogistica);

		        
		        
		        Long[] tiposLd = {941L, 940L, 939L, 938L};

		        for (Long idTipoLd : tiposLd) {
		            Asignacion asignacion = new Asignacion();
		            ListadoDetalle tipo = listadoDetalleService.obtener(idTipoLd, contexto);
		            asignacion.setTipo(tipo);
		            asignacion.setContrato(existeContrato.getIdContrato());
		            asignacion.setFlagActivo(1L);

		            if (contexto.getUsuario() != null && contexto.getUsuario().getUsuario() != null) {
		                asignacion.setUsuCreacion(contexto.getUsuario().getUsuario());
		            } else {
		                asignacion.setUsuCreacion("system");
		            }

		            asignacion.setFecCreacion(new Date());
		            asignacion.setIpCreacion(contexto.getIp());

		            asignacionDao.save(asignacion);
		        }
	        }
	        existeContrato.setFechaInicioContrato(contratoActualizadoDto.getFechaInicioContrato());
	        existeContrato.setFechaFinalContrato(contratoActualizadoDto.getFechaFinalContrato());

	    }

	    Contrato contratoActualizado = contratoDao.save(existeContrato);

	    return mapToContratoFormDTO(contratoActualizado);
	}

	
	private ContratoDetalleDTO mapToContratoFormDTO(Contrato contrato) {
		ContratoDetalleDTO dto = new ContratoDetalleDTO();
        dto.setIdContrato(contrato.getIdContrato());

        if (contrato.getNumeroContrato() != null) {
            dto.setNumeroContratoSap(String.valueOf(contrato.getNumeroContrato()));
        }

        dto.setFechaSuscripcionContrato(contrato.getFechaSuscripcionContrato());
        dto.setFechaInicioContrato(contrato.getFechaInicioContrato());
        dto.setFechaFinalContrato(contrato.getFechaFinalContrato());

        return dto;
    }
	
    private static final Integer ESTADO_INICIAL_PENDIENTE = 566;
    private static final Integer ESTADO_NUEVO_ASIGNADO = 544;
    private static final Integer ESTADO_CONTRATO_APROBADO_FINAL = 2;
    private static final Integer ESTADO_CONTRATO_RECHAZADO = 3;
    private static final Integer ESTADO_CONTRATO_INICIAL = 1;

	
	@Transactional
	public void procesarAccionesMasivas(Contrato req) {
		if (req.getIds() == null || req.getIds().isEmpty()) {
			throw new IllegalArgumentException("La lista de IDs no puede ser nula o vacía.");
		}
		boolean esAprobacion = "APROBAR".equalsIgnoreCase(req.getAccion());

		ListadoDetalle detAprobado = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.APROBADO);
		ListadoDetalle detRechazado = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.RECHAZADO);
		ListadoDetalle detAsignado = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);

		final Long TIPO_1 = 938L;
		final Long TIPO_2 = 939L;
		final Long TIPO_3 = 940L;
		final Long TIPO_4 = 941L;
		
		   // Estado final de revisión concluida
	    ListadoDetalle estadoRevisionConcluido = listadoDetalleService.obtenerListadoDetalle(
	        Constantes.LISTADO.ESTADO_REVISION.CODIGO,
	        Constantes.LISTADO.ESTADO_REVISION.CONCLUIDO);

		for (Long idContrato : req.getIds()) {
			contratoDao.findById(idContrato).ifPresent(contrato -> {
				Long tipoActual = null;

				if (contrato.getEstadoEvalLog() != null && contrato.getEstadoEvalLog().getIdListadoDetalle()
						.equals(detAsignado.getIdListadoDetalle())) {
					tipoActual = TIPO_1;
					contrato.setEstadoEvalLog(esAprobacion ? detAprobado : detRechazado);

				} else if (contrato.getEstadoEvalGaf() != null && contrato.getEstadoEvalGaf().getIdListadoDetalle()
						.equals(detAsignado.getIdListadoDetalle())) {
					tipoActual = TIPO_2;
					contrato.setEstadoEvalGaf(esAprobacion ? detAprobado : detRechazado);

				} else if (contrato.getEstadoEvalUni() != null && contrato.getEstadoEvalUni().getIdListadoDetalle()
						.equals(detAsignado.getIdListadoDetalle())) {
					tipoActual = TIPO_3;
					contrato.setEstadoEvalUni(esAprobacion ? detAprobado : detRechazado);

				} else if (contrato.getEstadoEvalApr() != null && contrato.getEstadoEvalApr().getIdListadoDetalle()
						.equals(detAsignado.getIdListadoDetalle())) {
					tipoActual = TIPO_4;
					contrato.setEstadoEvalApr(esAprobacion ? detAprobado : detRechazado);
				}

				if (tipoActual == null) {
					return;
				}

				Asignacion actual = asignacionDao.findByContratoAndTipo(idContrato, tipoActual);
				if (actual != null) {
					actual.setEvaluacion(esAprobacion ? detAprobado : detRechazado);
					actual.setObservacion(req.getObservacion());
					actual.setFechaAprobacion(new Date());
					asignacionDao.save(actual);
				}

				if (esAprobacion) {
	                if (tipoActual.equals(TIPO_4)) {
	                    contrato.setIdEstadoEval(estadoRevisionConcluido);
	                } else {
	                    Long tipoSiguiente = null;
	                    if (tipoActual.equals(TIPO_1)) tipoSiguiente = TIPO_2;
	                    else if (tipoActual.equals(TIPO_2)) tipoSiguiente = TIPO_3;
	                    else if (tipoActual.equals(TIPO_3)) tipoSiguiente = TIPO_4;

	                    if (tipoSiguiente != null) {
	                        Asignacion siguiente = asignacionDao.findByContratoAndTipo(idContrato, tipoSiguiente);
	                        if (siguiente != null) {
	                            siguiente.setEvaluacion(detAsignado);
	                            siguiente.setFechaRegistro(new Date());
	                            asignacionDao.save(siguiente);
	                        }
	                        if (tipoSiguiente.equals(TIPO_2)) {
	                            contrato.setEstadoEvalGaf(detAsignado);
	                        } else if (tipoSiguiente.equals(TIPO_3)) {
	                            contrato.setEstadoEvalUni(detAsignado);
	                        } else if (tipoSiguiente.equals(TIPO_4)) {
	                            contrato.setEstadoEvalApr(detAsignado);
	                        }
	                    }
	                }
	            }

				contratoDao.save(contrato);
			});
		}
	}



}
