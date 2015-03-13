package mx.com.villavicencio.almacen.system.venta.nota.detalle.factory;

import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class DetalleNotaVentaFactory {

    public static DtoDetalleNotaVenta newInstance() {
        return new DtoDetalleNotaVenta();
    }

    public static DtoDetalleNotaVenta newInstance(Integer id) {
        DtoDetalleNotaVenta detalleNotaVenta = newInstance();
        detalleNotaVenta.setIdNotaVenta(id);
        return detalleNotaVenta;
    }

    public static DtoDetalleNotaVenta newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
