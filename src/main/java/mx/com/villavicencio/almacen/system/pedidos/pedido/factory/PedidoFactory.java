package mx.com.villavicencio.almacen.system.pedidos.pedido.factory;

import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class PedidoFactory {

    public static DtoPedido newInstance() {
        return new DtoPedido();
    }

    public static DtoPedido newInstance(Integer id) {
        DtoPedido pedido = newInstance();
        pedido.setIdPedido(id);
        return pedido;
    }

    public static DtoPedido newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
