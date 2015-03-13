package mx.com.villavicencio.almacen.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.properties.PropertiesBean;

/**
 *
 * @author Gabriel J
 */
public class NumberUtils {

    private static final BigDecimal PORCENTAJE = new BigDecimal(100.00);

    public NumberUtils() {
    }

    public static boolean isNumeric(String cadena) {
        return Character.isDigit(cadena.charAt(0));
    }

    public static Integer stringToNumber(String cadena) {
        try {
            return Integer.parseInt(cadena);
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static BigDecimal stringToBigDecimal(String cadena) {
        BigDecimal bigDecimal;
        try {
            if (cadena.contains("$")) {
                String replace = cadena.replace("$", "");
                String replace1 = replace.replace(",", "");
                bigDecimal = new BigDecimal(replace1).setScale(2, BigDecimal.ROUND_DOWN);
            } else {
                bigDecimal = new BigDecimal(cadena).setScale(2, BigDecimal.ROUND_DOWN);
            }
            return bigDecimal;
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static BigDecimal integerToBigDecimal(Integer cadena) {
        try {
            BigDecimal bigDecimal = new BigDecimal(cadena).setScale(2, RoundingMode.HALF_DOWN);
            return bigDecimal;
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static BigDecimal convertNumberToPorcentaje(BigDecimal porcentaje) {
        if (porcentaje.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        BigDecimal divide = porcentaje.divide(PORCENTAJE).setScale(2, BigDecimal.ROUND_DOWN);
        return divide;
    }

    public static BigDecimal convertPorcentajeToNumber(BigDecimal porcentaje) {
        if (porcentaje != null) {
            return porcentaje.multiply(PORCENTAJE);
        } else {
            return new BigDecimal(BigInteger.ZERO);
        }
    }

    public static String formatMoney(BigDecimal number) {
        Locale locale = new Locale("es", "MX");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(number);
    }

    public static String formatMiles(BigDecimal number) {
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formateador = new DecimalFormat("###,###.##", simbolo);
        String format = formateador.format(number);
        return format;
    }

    public static boolean isReallyEmptyOrNull(Integer num) {
        return num == null;
    }
}
