package mx.com.villavicencio.almacen.system.pedidos.pedido.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;

/**
 *
 * @author Gabriel J
 */

public interface PedidoDao extends GenericDao<DtoPedido>{

    void deleteProducto(DtoPedido object);
    
    void updateStatus(DtoPedido object);
}
