package mx.com.villavicencio.almacen.system.movimientos.cargos.factory;

import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class CargosFactory {

    public static DtoCargos newInstance() {
        return new DtoCargos();
    }

    public static DtoCargos newInstance(Integer id) {
        DtoCargos cargos = newInstance();
        cargos.setIdCargos(id);
        return cargos;
    }

    public static DtoCargos newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
