package mx.com.villavicencio.almacen.system.pedidos.detalle.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;


/**
 *
 * @author Gabriel J
 */
public interface DetalleBo extends GenericBo<DtoUsuario, DtoDetallePedido> {

    DtoDetallePedido insert(DtoUsuario user, DtoDetallePedido object);
}
