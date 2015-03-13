package mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.factory;

import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class DetalleDevolucionesFactory {

    public static DtoDetalleDevoluciones newInstance() {
        return new DtoDetalleDevoluciones();
    }

    public static DtoDetalleDevoluciones newInstance(Integer id) {
        DtoDetalleDevoluciones devoluciones = newInstance();
        devoluciones.setIdDevoluciones(id);
        return devoluciones;
    }

    public static DtoDetalleDevoluciones newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
