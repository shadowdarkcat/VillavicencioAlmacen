package mx.com.villavicencio.almacen.system.pedidos.detalle.factory;

import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class DetallePedidoFactory {

    public static DtoDetallePedido newInstance() {
        return new DtoDetallePedido();
    }

    public static DtoDetallePedido newInstance(Integer id) {
        DtoDetallePedido detallePedido = newInstance();
        detallePedido.setIdDetallePedido(id);
        return detallePedido;
    }

    public static DtoDetallePedido newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
