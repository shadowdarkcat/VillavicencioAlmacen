package mx.com.villavicencio.almacen.system.inventario.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;

/**
 *
 * @author Gabriel J
 */

public interface InventarioDao extends GenericDao<DtoInventario>{

    Boolean isAgotado(DtoInventario object);
    
    DtoInventario getExistencia(DtoInventario object);
}
