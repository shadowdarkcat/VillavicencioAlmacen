package mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface DevolucionesBo extends GenericBo<DtoUsuario, DtoDevoluciones>{
    
    Boolean verifyExistDevolucion(DtoUsuario user, DtoNotaVenta object);
}
