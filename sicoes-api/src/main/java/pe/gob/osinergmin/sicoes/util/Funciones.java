package pe.gob.osinergmin.sicoes.util;

import java.util.Collection;

public class Funciones {

    public static String encriptar(String key, String password) {
        if (Funciones.esVacio(key) && Funciones.esVacio(password)) {
            return null;
        }
        Encriptador encriptador = new Encriptador(key);
        String encriptado = encriptador.encrypt(password);
        return encriptado;
    }

    public static String desencriptar(String key, String password) {
        if (Funciones.esVacio(key) && Funciones.esVacio(password)) {
            return null;
        }
        Encriptador encriptador = new Encriptador(key);
        String desencriptado = encriptador.decrypt(password);
        return desencriptado;
    }
    
    @SuppressWarnings("rawtypes")
    public static boolean esVacio(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).trim().equals("") || ((String) value).trim().equals("-")
                    || ((String) value).trim().equals("false");
        }
        if (value instanceof Object[]) {
            return ((Object[]) value).length < 0;
        }
        if (value instanceof Collection) {
            return ((Collection) value).isEmpty();
        }
        if (value instanceof Integer) {
            return ((Integer) value) == 0;
        }
        if (value instanceof Double) {
            return ((Double) value) == 0;
        }
        return false;
    }

}
