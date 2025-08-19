package pe.gob.osinergmin.sicoes.consumer;

import java.util.Date;
import java.util.List;

import gob.osinergmin.sne.domain.dto.rest.in.AfiliacionInRO;
import gob.osinergmin.sne.domain.dto.rest.out.AfiliacionOutROsne;
import pe.gob.osinergmin.sicoes.model.dto.DetalleVacacionesDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionVvoBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionVvoOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.NotificacionBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.VacacionesBeanDTO;

public interface SneApiConsumer {

	public String obtenerTokenAcceso() throws Exception;
	public AfiliacionOutRO obtenerAfiliacion(AfiliacionBeanDTO afiliacionBeanDTO, String token) throws Exception;
	public AfiliacionOutROsne obtenerAfiliacionSNE(AfiliacionInRO afiliacionBeanDTO, String token) throws Exception;
	public AfiliacionVvoOutRO registrarAfiliacion(AfiliacionVvoBeanDTO afiliacionBeanDTO, String token) throws Exception;
	public String obtenerTokenAcceso2() throws Exception;
	public Date obtenerFechaNotificacion(NotificacionBeanDTO notificacionBeanDTO, String token) throws Exception;
	public List<DetalleVacacionesDTO> obtenerListadoVacaciones() throws Exception;
}
