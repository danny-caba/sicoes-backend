package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.PerfilAprobadorService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class ListarInformeRenovacionContratoImpl {
    private final PerfilAprobadorService perfilAprobadorService;
    private final ListadoDetalleService listadoDetalleService;
    private final InformeRenovacionContratoDao informeRenovacionContratoDao;


       public ListarInformeRenovacionContratoImpl(
        PerfilAprobadorService perfilAprobadorService,
        ListadoDetalleService listadoDetalleService,
        InformeRenovacionContratoDao informeRenovacionContratoDao) {
           this.perfilAprobadorService = perfilAprobadorService;
           this.listadoDetalleService = listadoDetalleService;
           this.informeRenovacionContratoDao = informeRenovacionContratoDao;
       }

    public Page<InformeRenovacionContratoDTO> ejecutar(
            String numeroExpediente, 
            Long estado,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Boolean esVigente = true;
        Usuario usuarioCtx = contexto.getUsuario();

        ListadoDetalle estadoLd = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION"    ,
                "ASIGNADO"
        );
        ListadoDetalle grupoG1 = listadoDetalleService.obtenerListadoDetalle(
                "GRUPOS"    ,
                "G1"
        );

        if (estadoLd == null) {
            throw  new RuntimeException("Estado 'renovacion contrato' no encontrado en listado detalle");
        }

    // Inicializar la query
    Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins(
            numeroExpediente,
            esVigente,
            estado,
            idContratista,
            pageable
    );

    // Determinar qué filtro aplicar
    if (usuarioCtx.getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO))) {

        if (perfilAprobadorService.esPerfilAprobadorG1(usuarioCtx.getIdUsuario())) {
            listInforme = filtrarAprobaciones(listInforme,
                    estadoLd.getIdListadoDetalle(),
                    grupoG1.getIdListadoDetalle());

        } else if (perfilAprobadorService.esPerfilAprobadorG2(usuarioCtx.getIdUsuario())) {
            listInforme = filtrarAprobaciones(listInforme, 959L, 955L);
        }
    }

        return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
    }

    
    private Page<InformeRenovacionContrato> filtrarAprobaciones(Page<InformeRenovacionContrato> informes,
                                                            Long estadoFiltro,
                                                            Long grupoFiltro) {
    return informes.map(informe -> {
        List<RequerimientoAprobacion> aprobacionesFiltradas = informe.getAprobaciones().stream()
                .filter(reqAprob -> reqAprob.getIdEstadoLd().equals(estadoFiltro)
                        && reqAprob.getIdGrupoAprobadorLd().equals(grupoFiltro))
                .collect(Collectors.toList());

        informe.setAprobaciones(aprobacionesFiltradas);
        return informe;
    });
    }
}
