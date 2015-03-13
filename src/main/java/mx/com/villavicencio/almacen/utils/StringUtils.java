package mx.com.villavicencio.almacen.utils;

import java.math.BigDecimal;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.properties.PropertiesBean;

/**
 *
 * @author Gabriel J
 */
public class StringUtils {

    public static boolean isReallyEmptyOrNull(String cadena) {
        return cadena == null || cadena.trim().isEmpty() || "null".equals(cadena);
    }

    public static boolean isReallyEmptyOrNull(String[] cadena) {
        return cadena == null || cadena.length == 0;
    }

    public static boolean isSpace(String cadena) {
        return Character.isSpaceChar(cadena.charAt(0));
    }

    public static String numberToString(Integer cadena) {
        try {
            return String.valueOf(cadena);
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }
    
    public static String numberToString(BigDecimal cadena) {
        try {
            return String.valueOf(cadena.intValueExact());
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static String replace(String string) {
        string = string.replace("[", "");
        string = string.replace("]", "");
        return string;
    }

    public static String[] split(String string) {
        String[] split = string.split(",");
        return split;
    }
    
    public static Character stringToChar(String str) {
        return str.charAt(0);
    }
}
