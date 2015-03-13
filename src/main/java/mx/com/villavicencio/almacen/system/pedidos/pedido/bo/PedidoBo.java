package mx.com.villavicencio.almacen.system.pedidos.pedido.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface PedidoBo extends GenericBo<DtoUsuario, DtoPedido>{

    void deleteProducto(DtoUsuario user, DtoPedido object);
    
    void updateStatus(DtoUsuario user, DtoPedido object);
}
