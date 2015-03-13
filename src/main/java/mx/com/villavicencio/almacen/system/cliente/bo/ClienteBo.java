package mx.com.villavicencio.almacen.system.cliente.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface ClienteBo extends GenericBo<DtoUsuario, DtoCliente> {

    Collection<DtoCliente> findByIdVendedor(DtoUsuario user);
}
