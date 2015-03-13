package mx.com.villavicencio.almacen.system.venta.nota.nota.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface NotaVentaDao extends GenericDao<DtoNotaVenta> {

    DtoNotaVenta insert(DtoNotaVenta object);
}
