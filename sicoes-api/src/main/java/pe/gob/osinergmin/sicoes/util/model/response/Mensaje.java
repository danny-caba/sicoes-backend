package pe.gob.osinergmin.sicoes.util.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Clase para representar mensajes de respuesta en el sistema SICOES
 * Requerimiento 350 - Renovación de Contratos
 * @project sicoes-api
 * @date 15/08/2025
 **/

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mensaje {
    /**
     * Código del mensaje, utilizado para identificar el tipo de mensaje.
     */
    @JsonProperty("codigo")
    String codigo;

    /**
     * Tipo del mensaje (por ejemplo, "ERROR", "INFO", etc.).
     */
    @JsonProperty("tipo")
    String tipo;

    /**
     * Contenido del mensaje.
     */
    @JsonProperty("message")
    String message;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}