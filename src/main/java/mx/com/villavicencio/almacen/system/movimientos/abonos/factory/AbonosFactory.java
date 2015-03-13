package mx.com.villavicencio.almacen.system.movimientos.abonos.factory;

import mx.com.villavicencio.almacen.system.movimientos.abonos.dto.DtoAbonos;

/**
 *
 * @author Gabriel J
 */
public class AbonosFactory {

    public static DtoAbonos newInstance() {
        return new DtoAbonos();
    }
}
