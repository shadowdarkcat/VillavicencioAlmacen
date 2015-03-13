package mx.com.villavicencio.almacen.system.movimientos.cargos.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface CargosBo extends GenericBo<DtoUsuario, DtoCargos>{

    DtoCargos insert(DtoUsuario user, DtoCargos object);
}
