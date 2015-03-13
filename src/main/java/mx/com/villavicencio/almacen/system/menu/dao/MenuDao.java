package mx.com.villavicencio.almacen.system.menu.dao;

import java.util.Collection;
import mx.com.villavicencio.almacen.system.menu.dto.MenuItem;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface MenuDao {

    Collection<MenuItem> getMenu(DtoUsuario user);

    Collection<MenuItem> getMenuPrivilegios(DtoUsuario user);

    void insertMultiplesPrivilegios(DtoUsuario usuario, Collection<MenuItem> nuevoMenu);

    void insertPrivilegio(DtoUsuario usuario, MenuItem menuItem);

    void deletePrivilegios(DtoUsuario usuario);
}
