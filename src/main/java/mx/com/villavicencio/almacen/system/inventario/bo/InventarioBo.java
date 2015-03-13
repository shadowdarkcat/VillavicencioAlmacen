package mx.com.villavicencio.almacen.system.inventario.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface InventarioBo extends GenericBo<DtoUsuario, DtoInventario>{

    Boolean isAgotado(DtoUsuario user, DtoInventario object);
    
    DtoInventario getExistencia(DtoUsuario user, DtoInventario object);
}
