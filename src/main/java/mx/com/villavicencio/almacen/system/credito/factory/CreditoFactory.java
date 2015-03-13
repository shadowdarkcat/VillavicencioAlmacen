package mx.com.villavicencio.almacen.system.credito.factory;

import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class CreditoFactory {

    public static DtoCredito newInstance() {
        return new DtoCredito();
    }

    public static DtoCredito newInstance(Integer id) {
        DtoCredito credito = newInstance();
        credito.setIdCredito(id);
        return credito;
    }

    public static DtoCredito newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
