package mx.com.villavicencio.almacen.system.venta.nota.detalle.dao;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface DetalleNotaVentaDao extends GenericDao<DtoDetalleNotaVenta>{

    Collection<DtoDetalleNotaVenta> findAll(DtoDetalleNotaVenta object);
}
