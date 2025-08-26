package pe.gob.osinergmin.sicoes.util.renovacioncontrato;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponseMeta;
import pe.gob.osinergmin.sicoes.util.model.response.Mensaje;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ResponseBuilder {
    private ResponseBuilder() {
        // Private constructor to prevent instantiation
    }
    public static ResponseEntity<ApiResponse> buildResponse(ApiResponse apiResponse, String status, int code, String message, HttpStatus httpStatus, List<?> data) {
        apiResponse.setMeta(new ApiResponseMeta(status, data.size(), data.size(), Arrays.asList(new Mensaje(String.valueOf(code), status, message)), Collections.singletonMap(
                "timestamp", System.currentTimeMillis()
        )));
        apiResponse.setData(data);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    public static ResponseEntity<ApiResponse> buildErrorResponse(ApiResponse apiResponse, String status, int code, String message, HttpStatus httpStatus) {
        apiResponse.setMeta(new ApiResponseMeta(status, 0, 0, Arrays.asList(new Mensaje(String.valueOf(code), "ERROR", message)), Collections.singletonMap(
                "timestamp", System.currentTimeMillis()
        )));
        apiResponse.setData(null);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}