package mx.com.villavicencio.almacen.system.cliente.factory;

import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class ClienteFactory {

    public static DtoCliente newInstance() {
        return new DtoCliente();
    }

    public static DtoCliente newInstance(Integer id) {
        DtoCliente cliente = newInstance();
        cliente.setIdCliente(id);
        return cliente;
    }

    public static DtoCliente newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
