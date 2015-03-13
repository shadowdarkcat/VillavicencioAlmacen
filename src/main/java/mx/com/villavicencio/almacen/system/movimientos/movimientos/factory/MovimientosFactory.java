package mx.com.villavicencio.almacen.system.movimientos.movimientos.factory;

import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;

/**
 *
 * @author Gabriel J
 */

public class MovimientosFactory {

    public static DtoMovimientos newInstance(){
        return new DtoMovimientos();
    }
}
