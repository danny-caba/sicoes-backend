package pe.gob.osinergmin.sicoes.util.model.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;

/**
 * Modelo para almacenar información del cliente en las respuestas del sistema SICOES
 * Requerimiento 350 - Renovación de Contratos
 * @project sicoes-api
 * @date 15/08/2025
 */

public class ClientInfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ipClient")
    private String ipClient;

    @JsonProperty("userClient")
    private String userClient;

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public String getUserClient() {
        return userClient;
    }

    public void setUserClient(String userClient) {
        this.userClient = userClient;
    }
}