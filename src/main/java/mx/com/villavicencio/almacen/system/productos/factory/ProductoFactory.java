package mx.com.villavicencio.almacen.system.productos.factory;

import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;


/**
 *
 * @author Gabriel J
 */
public class ProductoFactory {

    public static final DtoProducto newInstance() {
        return new DtoProducto();
    }

    public static final DtoProducto newInstance(Integer id) {
        DtoProducto producto = newInstance();
        producto.setIdProducto(id);
        return producto;
    }

    public static final DtoProducto newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
