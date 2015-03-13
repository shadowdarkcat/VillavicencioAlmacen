package mx.com.villavicencio.almacen.system.pedidos.detalle.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;

/**
 *
 * @author Gabriel J
 */
public interface DetallesDao extends GenericDao<DtoDetallePedido> {

    DtoDetallePedido insert(DtoDetallePedido object);
}
