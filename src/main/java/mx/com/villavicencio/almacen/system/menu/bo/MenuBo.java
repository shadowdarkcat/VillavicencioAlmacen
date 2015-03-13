package mx.com.villavicencio.almacen.system.menu.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.system.menu.dto.MenuItem;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface MenuBo {

    Collection<MenuItem> getMenu(DtoUsuario user);

    Collection<MenuItem> getMenuPrivilegios(DtoUsuario user);

    String getConvercionMenu(DtoUsuario user);
   
}
