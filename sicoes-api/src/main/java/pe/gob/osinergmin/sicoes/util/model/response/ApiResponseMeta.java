package pe.gob.osinergmin.sicoes.util.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.Map;

/**
 * Clase para metadatos de respuestas de la API del sistema SICOES
 * Requerimiento 350 - Renovación de Contratos
 * @project sicoes-api
 * @date 15/08/2025
 **/

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseMeta {

    /**
     * Resultado de la operación.
     */
    @JsonProperty("result")
    private String result;

    /**
     * Cantidad de registros devueltos.
     */
    @JsonProperty("cantidadRegistros")
    private Integer cantidadRegistros;

    /**
     * Cantidad de registros total.
     */
    @JsonProperty("cantidadRegistrosTotal")
    private Integer cantidadRegistrosTotal;

    /**
     * Lista de mensajes asociados con la respuesta.
     */
    @JsonProperty("mensajes")
    private List<Mensaje> mensajes;

    /**
     * Atributos adicionales asociados con la respuesta.
     */
    @JsonProperty("atributos")
    private Map<String, Object> atributos;

}

