package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PlazoConfirmacionService {

    /**
     * Lista todos los plazos de confirmación activos
     * @return Lista de plazos de confirmación
     */
    List<PlazoConfirmacion> listar();

    /**
     * Lista plazos de confirmación con paginación
     * @param pageable Configuración de paginación
     * @return Página de plazos de confirmación
     */
    Page<PlazoConfirmacion> listarPaginado(Pageable pageable);

    /**
     * Obtiene un plazo de confirmación por ID
     * @param id ID del plazo de confirmación
     * @return Plazo de confirmación
     */
    PlazoConfirmacion obtenerPorId(Long id);

    /**
     * Crea un nuevo plazo de confirmación
     * @param plazoDTO Datos del plazo de confirmación
     * @param contexto Contexto del usuario
     * @return Plazo de confirmación creado
     */
    PlazoConfirmacion crear(PlazoConfirmacionDTO plazoDTO, Contexto contexto);

    /**
     * Actualiza un plazo de confirmación existente
     * @param id ID del plazo de confirmación
     * @param plazoDTO Datos actualizados
     * @param contexto Contexto del usuario
     * @return Plazo de confirmación actualizado
     */
    PlazoConfirmacion actualizar(Long id, PlazoConfirmacionDTO plazoDTO, Contexto contexto);

    /**
     * Elimina (desactiva) un plazo de confirmación
     * @param id ID del plazo de confirmación
     * @param contexto Contexto del usuario
     */
    void eliminar(Long id, Contexto contexto);

    /**
     * Lista plazos por tipo de día
     * @param tipoDia Tipo de día (1: Calendario, 2: Hábil)
     * @return Lista de plazos de confirmación
     */
    List<PlazoConfirmacion> listarPorTipoDia(Integer tipoDia);

    /**
     * Lista plazos por número de días
     * @param dias Número de días
     * @return Lista de plazos de confirmación
     */
    List<PlazoConfirmacion> listarPorDias(Integer dias);
}
