package pe.gob.osinergmin.sicoes.util;

public class ComparisonUtil {

    public static <T> boolean sonIguales(T obj1, T obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }
}
