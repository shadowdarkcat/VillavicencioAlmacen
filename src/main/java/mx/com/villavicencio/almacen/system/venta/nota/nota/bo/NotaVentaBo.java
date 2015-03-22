package mx.com.villavicencio.almacen.system.venta.nota.nota.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface NotaVentaBo extends GenericBo<DtoUsuario, DtoNotaVenta> {

    DtoNotaVenta insert(DtoUsuario user, DtoNotaVenta object);

    Integer findByFolio(DtoUsuario user, DtoNotaVenta object);
}
