package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

/**
 * DTO para las respuestas de búsqueda de Supervisoras
 * Requerimiento 350 - Renovación de Contratos
 * @project sicoes-api
 * @date 15/08/2025
 */
public class SupervisoraResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idSupervisora;
    private Long idTipoPersonaLd;
    private String tipoPersonaNombre;
    private String nombreRazonSocial;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String etiqueta;

    // Constructores
    public SupervisoraResponseDTO() {
    }

    public SupervisoraResponseDTO(Long idSupervisora, Long idTipoPersonaLd, String tipoPersonaNombre,
            String nombreRazonSocial, String nombres, String apellidoPaterno, String apellidoMaterno, String etiqueta) {
        this.idSupervisora = idSupervisora;
        this.idTipoPersonaLd = idTipoPersonaLd;
        this.tipoPersonaNombre = tipoPersonaNombre;
        this.nombreRazonSocial = nombreRazonSocial;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.etiqueta = etiqueta;
    }

    // Getters y Setters
    public Long getIdSupervisora() {
        return idSupervisora;
    }

    public void setIdSupervisora(Long idSupervisora) {
        this.idSupervisora = idSupervisora;
    }

    public Long getIdTipoPersonaLd() {
        return idTipoPersonaLd;
    }

    public void setIdTipoPersonaLd(Long idTipoPersonaLd) {
        this.idTipoPersonaLd = idTipoPersonaLd;
    }

    public String getTipoPersonaNombre() {
        return tipoPersonaNombre;
    }

    public void setTipoPersonaNombre(String tipoPersonaNombre) {
        this.tipoPersonaNombre = tipoPersonaNombre;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return "SupervisoraResponseDTO [idSupervisora=" + idSupervisora + ", idTipoPersonaLd=" + idTipoPersonaLd
                + ", tipoPersonaNombre=" + tipoPersonaNombre + ", nombreRazonSocial=" + nombreRazonSocial
                + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
                + apellidoMaterno + ", etiqueta=" + etiqueta + "]";
    }
}
