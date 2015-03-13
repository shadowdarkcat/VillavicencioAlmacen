package mx.com.villavicencio.almacen.system.movimientos.movimientos.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface MovimientosBo extends GenericBo<DtoUsuario, DtoMovimientos> {

    Boolean verifyExistDevolucion(DtoUsuario user, DtoMovimientos object);

    DtoMovimientos findByIdDevolucion(DtoUsuario user, DtoMovimientos object);
}
