package pe.gob.osinergmin.sicoes.util;

import pe.gob.osinergmin.sicoes.model.Solicitud;

public class ValidacionUtil {
	
	public static void validarPresentacion(Contexto contexto, Solicitud solicitudBD) {
		
		if(Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO.equals(solicitudBD.getEstado().getCodigo()) &&
				contexto.getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO))
			return;

		if(!(Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR.equals(solicitudBD.getEstado().getCodigo())||
			Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO.equals(solicitudBD.getEstado().getCodigo()) ||
			Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO.equals(solicitudBD.getEstado().getCodigo()))) {
			if(contexto.getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_NO_PUEDE_EDITAR_SOLICITUD);
			}
		}
	}

}
