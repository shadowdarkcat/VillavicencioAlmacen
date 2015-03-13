package mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;

/**
 *
 * @author Gabriel J
 */
public interface DetalleDevolucionesBo extends GenericBo<DtoUsuario, DtoDetalleDevoluciones>{

    Collection<DtoDetalleDevoluciones> findAll(DtoUsuario user, DtoDetalleDevoluciones object);
}
