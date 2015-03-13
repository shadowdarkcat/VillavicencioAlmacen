package mx.com.villavicencio.almacen.system.productos.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface ProductoBo extends GenericBo<DtoUsuario, DtoProducto> {

    Boolean verifyExist(DtoUsuario user, DtoProducto object);

    Collection<DtoProducto> findDatosProductoEstablecidoVendedorById(DtoUsuario user, Integer id);

    Collection<DtoProducto> findDatosProductoPersonalizadoVendedorById(DtoUsuario user, Integer id);

    DtoProducto insertPersonalizado(DtoUsuario user, DtoProducto personalizado);

    DtoProducto findProductoPersonalizadoVendedorById(DtoUsuario user, DtoProducto producto, Integer id);

    Collection<DtoProducto> findDatosProductoEstablecidoClienteById(DtoUsuario user, Integer id);

    Collection<DtoProducto> findDatosProductoPersonalizadoClienteById(DtoUsuario user, Integer id);

    DtoProducto findProductoPersonalizadoClienteById(DtoUsuario user, DtoProducto producto, Integer id);
    
    DtoProducto findProductoEstablecidoClienteById(DtoUsuario user, DtoProducto producto, Integer id);
    
    DtoProducto findProductoEstablecidoVendedorById(DtoUsuario user, DtoProducto producto, Integer id);
}
