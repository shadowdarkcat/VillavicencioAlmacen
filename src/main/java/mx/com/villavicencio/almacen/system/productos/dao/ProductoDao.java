package mx.com.villavicencio.almacen.system.productos.dao;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;


/**
 *
 * @author Gabriel J
 */
public interface ProductoDao extends GenericDao<DtoProducto> {

    Boolean verifyExist(DtoProducto object);

    Collection<DtoProducto> findDatosProductoEstablecidoVendedorById(Integer id);

    Collection<DtoProducto> findDatosProductoPersonalizadoVendedorById(Integer id);

    DtoProducto insertPersonalizado(DtoProducto personalizado);
   
    DtoProducto findProductoPersonalizadoVendedorById(DtoProducto producto, Integer id);
    
    Collection<DtoProducto> findDatosProductoEstablecidoClienteById(Integer id);

    Collection<DtoProducto> findDatosProductoPersonalizadoClienteById(Integer id);
   
    DtoProducto findProductoPersonalizadoClienteById(DtoProducto producto, Integer id);
    
    DtoProducto findProductoEstablecidoClienteById(DtoProducto producto, Integer id);
    
    DtoProducto findProductoEstablecidoVendedorById(DtoProducto producto, Integer id);
}
