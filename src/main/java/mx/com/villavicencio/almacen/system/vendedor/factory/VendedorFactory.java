package mx.com.villavicencio.almacen.system.vendedor.factory;

import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class VendedorFactory {

    public static DtoVendedor newInstance() {
        return new DtoVendedor();
    }

    public static DtoVendedor newInstance(Integer id) {
        DtoVendedor vendedor = newInstance();
        vendedor.setIdVendedor(id);
        return vendedor;
    }

    public static DtoVendedor newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
