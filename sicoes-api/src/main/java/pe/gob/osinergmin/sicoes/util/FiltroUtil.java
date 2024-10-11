package pe.gob.osinergmin.sicoes.util;

import org.apache.commons.lang3.StringUtils;

public class FiltroUtil {
	
	public static String datoString(String dato) {
		if(StringUtils.isBlank(dato)) {dato="";}
		dato="%"+dato+"%";
		return dato.toUpperCase();
	}
}
