package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.HashMap;
import java.util.Map;

@Service
public class RequerimientoInvitacionServiceTest {
    
    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;
    
    @Autowired
    private ListadoDetalleDao listadoDetalleDao;
    
    @Transactional(readOnly = true)
    public Map<String, Object> testBuscarInvitacion(Long idReqInvitacion) {
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            resultado.put("paso", "1. Iniciando búsqueda");
            resultado.put("idBuscando", idReqInvitacion);
            
            if (idReqInvitacion == null) {
                resultado.put("error", "ID es null");
                return resultado;
            }
            
            resultado.put("paso", "2. Buscando en BD");
            RequerimientoInvitacion invitacion = requerimientoInvitacionDao.findById(idReqInvitacion).orElse(null);
            
            if (invitacion == null) {
                resultado.put("error", "No se encontró invitación con ID: " + idReqInvitacion);
                return resultado;
            }
            
            resultado.put("paso", "3. Invitación encontrada");
            resultado.put("invitacionId", invitacion.getIdReqInvitacion());
            
            // Verificar estado
            resultado.put("paso", "4. Verificando estado");
            if (invitacion.getIdEstadoLd() != null) {
                resultado.put("idEstadoLd", invitacion.getIdEstadoLd());
            } else {
                resultado.put("idEstadoLd", "null");
            }
            
            if (invitacion.getEstadoInvitacion() != null) {
                resultado.put("estadoInvitacion", "existe");
                resultado.put("estadoId", invitacion.getEstadoInvitacion().getIdListadoDetalle());
            } else {
                resultado.put("estadoInvitacion", "null");
            }
            
            // Verificar relaciones
            resultado.put("paso", "5. Verificando relaciones");
            if (invitacion.getIdReqRenovacion() != null) {
                resultado.put("idReqRenovacion", invitacion.getIdReqRenovacion());
            } else {
                resultado.put("idReqRenovacion", "null");
            }
            
            // Verificar si podemos cargar el ListadoDetalle
            resultado.put("paso", "6. Verificando catálogo de estados");
            try {
                if (invitacion.getIdEstadoLd() != null) {
                    ListadoDetalle estado = listadoDetalleDao.findById(invitacion.getIdEstadoLd()).orElse(null);
                    if (estado != null) {
                        resultado.put("estadoCodigo", estado.getCodigo());
                        resultado.put("estadoDescripcion", estado.getDescripcion());
                    } else {
                        resultado.put("estadoCatalogo", "No encontrado en catálogo");
                    }
                }
            } catch (Exception ex) {
                resultado.put("errorCatalogo", ex.getMessage());
            }
            
            resultado.put("exito", true);
            
        } catch (Exception e) {
            resultado.put("error", "Excepción: " + e.getClass().getName());
            resultado.put("mensaje", e.getMessage());
            resultado.put("causa", e.getCause() != null ? e.getCause().getMessage() : "sin causa");
        }
        
        return resultado;
    }
}