package mx.com.villavicencio.almacen.properties;

import java.util.Properties;

/**
 *
 * @author Gabriel J
 */
public class PropertiesBean {

    private static final Properties errorFile = ReadProperties.getPropertiesFromFile(Property.ERROR_FILE);
    private static final Properties infoFile = ReadProperties.getPropertiesFromFile(Property.INFO_FILE);
    private static final Properties leyendasFile = ReadProperties.getPropertiesFromFile(Property.LEYENDAS_FILE);
    private static final Properties posFile = ReadProperties.getPropertiesFromFile(Property.POS_FILE);

    public static Properties getErrorFile() {
        return errorFile;
    }

    public static Properties getInfoFile() {
        return infoFile;
    }
    
    public static Properties getLeyendasFile() {
        return leyendasFile;
    }

    public static Properties getPosFile() {
        return posFile;
    }
}
