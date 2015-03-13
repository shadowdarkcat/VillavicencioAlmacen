package mx.com.villavicencio.almacen.system.pedidos.datos.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.pedidos.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface DatosPedidoBo extends GenericBo<DtoUsuario, DtoDatosPedido> {

    Collection<DtoDatosPedido> findDatosById(DtoUsuario user, DtoDatosPedido object);
}
