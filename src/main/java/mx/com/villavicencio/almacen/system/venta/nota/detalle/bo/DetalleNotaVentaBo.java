package mx.com.villavicencio.almacen.system.venta.nota.detalle.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface DetalleNotaVentaBo extends GenericBo<DtoUsuario, DtoDetalleNotaVenta> {

    Collection<DtoDetalleNotaVenta> findAll(DtoUsuario user, DtoDetalleNotaVenta object);
}
