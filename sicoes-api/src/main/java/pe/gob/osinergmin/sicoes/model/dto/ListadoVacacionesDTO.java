package pe.gob.osinergmin.sicoes.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ListadoVacacionesDTO {

	private String status;
	
	private List<DetalleVacacionesDTO> result;
	
	public ListadoVacacionesDTO() {
		result = new ArrayList<>();
	}
	
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public List<DetalleVacacionesDTO> getResult() {
		return result;
	}

	public void setResult(List<DetalleVacacionesDTO> result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ListadoVacacionesDTO [status=" + status + ", result=" + result + ", message=" + message + "]";
	}
	
}
