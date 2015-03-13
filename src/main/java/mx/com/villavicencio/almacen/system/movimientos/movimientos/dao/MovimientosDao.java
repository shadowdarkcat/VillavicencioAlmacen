package mx.com.villavicencio.almacen.system.movimientos.movimientos.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;

/**
 *
 * @author Gabriel J
 */
public interface MovimientosDao extends GenericDao<DtoMovimientos> {

    Boolean verifyExistDevolucion(DtoMovimientos object);

    DtoMovimientos findByIdDevolucion(DtoMovimientos object);
}
