package pe.gob.osinergmin.sicoes.util.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Clase para encapsular respuestas de la API del sistema SICOES
 * Requerimiento 350 - Renovación de Contratos
 * @project sicoes-api
 * @date 15/08/2025
 **/

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    /**
     * Metadatos de la respuesta.
     */
    @JsonProperty("meta")
    private ApiResponseMeta meta;

    /**
     * Datos de la respuesta.
     */
    @JsonProperty("data")
    private Object data;

    public ApiResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(ApiResponseMeta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}