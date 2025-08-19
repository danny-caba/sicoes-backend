package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

/**
 * DTO para las peticiones de búsqueda de Supervisoras
 * Requerimiento 350 - Renovación de Contratos
 * @project sicoes-api
 * @date 15/08/2025
 */
public class SupervisoraRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombreSupervisora;

    // Constructores
    public SupervisoraRequestDTO() {
    }

    public SupervisoraRequestDTO(String nombreSupervisora) {
        this.nombreSupervisora = nombreSupervisora;
    }

    // Getters y Setters
    public String getNombreSupervisora() {
        return nombreSupervisora;
    }

    public void setNombreSupervisora(String nombreSupervisora) {
        this.nombreSupervisora = nombreSupervisora;
    }

    @Override
    public String toString() {
        return "SupervisoraRequestDTO [nombreSupervisora=" + nombreSupervisora + "]";
    }
}
