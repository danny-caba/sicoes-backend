package pe.gob.osinergmin.sicoes.util;

public class StringUtil {

	
	public static String obtnerExtension(String nombreArchivo) {
		if(nombreArchivo==null) {return "";}
		StringBuilder strb = new StringBuilder(nombreArchivo);
		String nombreArchivoReverse = strb.reverse().toString();
		
		String extension=nombreArchivoReverse.substring(0,nombreArchivoReverse.indexOf("."));
		return new StringBuilder(extension).reverse().toString();
	}
	
//	public static boolean isBlank(String valor) {
//		if(valor==null||valor.isBlank()||valor.isEmpty()) {
//			return true;
//		}
//		return false;
//	}
}
