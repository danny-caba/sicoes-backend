package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.util.Constantes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test-directo")
public class TestDirectoController {
    
    @Autowired
    private ListadoDetalleDao listadoDetalleDao;
    
    @GetMapping("/verificar-estado/{id}")
    public ResponseEntity<?> verificarEstado(@PathVariable Long id) {
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            // 1. Buscar por ID
            resultado.put("buscandoId", id);
            ListadoDetalle estado = listadoDetalleDao.findById(id).orElse(null);
            
            if (estado != null) {
                resultado.put("encontrado", true);
                resultado.put("codigo", estado.getCodigo());
                resultado.put("descripcion", estado.getDescripcion());
                resultado.put("nombre", estado.getNombre());
                resultado.put("esInvitado", Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(estado.getCodigo()));
            } else {
                resultado.put("encontrado", false);
            }
            
            // 2. Buscar estado ACEPTADO
            try {
                ListadoDetalle estadoAceptado = listadoDetalleDao.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_INVITACION.CODIGO, 
                    Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO
                );
                
                if (estadoAceptado != null) {
                    resultado.put("estadoAceptado", "encontrado");
                    resultado.put("aceptadoId", estadoAceptado.getIdListadoDetalle());
                } else {
                    resultado.put("estadoAceptado", "null");
                }
            } catch (Exception e) {
                resultado.put("errorBuscandoAceptado", e.getMessage());
            }
            
        } catch (Exception e) {
            resultado.put("error", e.getClass().getSimpleName());
            resultado.put("mensaje", e.getMessage());
        }
        
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}